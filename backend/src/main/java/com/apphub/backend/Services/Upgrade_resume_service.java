package com.apphub.backend.Services;


import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import com.apphub.backend.models.Upgrade_resume;
import com.apphub.backend.models.User;
import com.apphub.backend.repositories.Upgrade_resume_repository;
import com.apphub.backend.repositories.User_repository;


import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Service
public class Upgrade_resume_service {

    private final Upgrade_resume_repository upgrade_resume_repository;
    private final User_repository user_repository;
    private final S3Client s3Client;
    private final String resume_bucket="uploaded-resume-s3";
    private final WebClient client;
    private final S3Presigner s3Presigner;

    public Upgrade_resume_service(Upgrade_resume_repository upgrade_resume_repository, S3Client s3Client, User_repository user_repository, WebClient client, S3Presigner s3Presigner) {
        this.upgrade_resume_repository = upgrade_resume_repository;
        this.s3Client = s3Client;
        this.user_repository = user_repository;
        this.client = client;
        this.s3Presigner = s3Presigner;

    }
    
    public void upload_to_s3(String fileName, byte[] file_bytes)
    {
        try {
         PutObjectRequest request = PutObjectRequest.builder()
            .bucket(resume_bucket)
            .key(fileName)
            .build();

        s3Client.putObject(request,
            RequestBody.fromInputStream(
                    new ByteArrayInputStream(file_bytes),
                    file_bytes.length
            ));

        } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("S3 upload failed "+e.getMessage(), e);
       }

    }

    public long upload_resume(MultipartFile file, Long userId, String jobPosition) throws IOException{
        try {
        String fileName = "resumes/" +userId+ "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        byte[] file_bytes = file.getBytes();
        upload_to_s3(fileName, file_bytes);
       
        LocalDateTime uploadedAt = LocalDateTime.now();

        Upgrade_resume resume = save_resume(file, fileName, userId, jobPosition, uploadedAt);
       
        Long fileId = resume.getId();

        String originalFileName = file.getOriginalFilename();
        //save upgrades resume to s3
     
        improve_resume_with_ai(file, jobPosition, userId, originalFileName, fileId);
       
        return resume.getId();

        } catch (IOException e) {
          throw new IOException("Failed to process file upload", e);
        }
        
    }
    
//upload new resume to s3 after its done making it
    

    public void upload_upgraded_resume(Long userId, String original_file_name, byte[] upgraded_resume, Long fileId){
      
        String file_name = "upgraded-resumes/" +userId+ "/" + System.currentTimeMillis() + "_" +original_file_name;
        Upgrade_resume resume = upgrade_resume_repository.findById(fileId)
            .orElseThrow(()->new RuntimeException("file not found"));
            
        resume.setUpgradedResumeFileName(file_name);
        upgrade_resume_repository.save(resume);
        //getfileid and set file name for new resume
        upload_to_s3(file_name, upgraded_resume);

    }

    



    public Upgrade_resume save_resume(MultipartFile file, String fileName, Long userId, String jobPosition, LocalDateTime uploadedAt){
        try {
            
            User user = user_repository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
            String fileUrl = "https://" + resume_bucket + ".s3.amazonaws.com/" + fileName;
            String fileType = file.getContentType();
            double fileSize = Math.round((file.getSize() / 1024.0) * 100.0) / 100.0;
            Upgrade_resume resume = new Upgrade_resume(user, jobPosition, fileUrl, uploadedAt, fileName, fileType, fileSize);
         
            return upgrade_resume_repository.save(resume);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Saving resume data to table failed: "+e.getMessage());
        }
    }

    
    public void improve_resume_with_ai(MultipartFile file, String jobPosition, Long userId, String originalFileName, Long fileId){
        String resume_text = extract_text(file);
        String prompt = "You are an expert resume writer\r\n" + //
                        "Task:\r\n" + //
                        "Edit this resume to better match the job position: "+jobPosition+".\r\n" + //
                        "HARD RULES:\n" + //
                                                     
                                                        "- Resume must be 500 words total.\n" + //
                                                        "- NO fluff, NO filler words, NO repeated ideas.\n" + //
                                                        "- dont add fake information\n"+
                                                        "- Use short, direct bullet points only.\n" + //
                                                        "- Each bullet point must start on a new line\n" + //
                                                        "\n" + //
                                                        "FORMAT:\n" + //
                                                        "- Use and fully complete all sections (SUMMARY, SKILLS, EXPERIENCE, EDUCATION).\n" + //
                                                        "- No paragraphs allowed anywhere.\n" + //
                                                        "- make sure to keep their personal information the completly the same and at the top of the resume"+
                                                        "\n" +
                        "Resume:\r\n" + //
                        resume_text;
       Mono<byte[]> ai_response = client.post()
        .uri("/api/chat") 
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(Map.of(
                "model", "phi3",
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                ),
                "options", Map.of(
                    "temperature", 0.3,
                    "num_predict", 800
                ),
                "stream", false
        ))
        .retrieve()
        .bodyToMono(Map.class)
        .map(res -> {
          
            Map message = (Map) res.get("message");
            return message.get("content").toString();
        })
        .map(this::save_as_docx);
        ai_response.subscribe(bytes->{
          
            System.out.println("AI resume completed for User: "+userId);
            upload_upgraded_resume(userId, originalFileName, bytes, fileId);
        },
        error->{
           
            System.out.println("AI error processing resume: "+ error);
            error.printStackTrace();
        }

    );
       
           
    }

    //turns resume from text to file format
    public byte[] save_as_docx(String response){
        try (XWPFDocument document = new XWPFDocument();
            ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            String[] lines = response.split("\n");
            for(String line: lines){
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText(line);
            }
            document.write(out);
            return out.toByteArray();


        }
        catch (Exception e) {
            throw new RuntimeException("Failed to extract text :",e);
        }
        
    }


    public String extract_text(MultipartFile file){
        try (InputStream inputStream = file.getInputStream();
            XWPFDocument document = new XWPFDocument(inputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document)){
            return extractor.getText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract text :",e);
        }
    }

    public String get_presigned_url(long fileId){
        Upgrade_resume resume = upgrade_resume_repository.findById(fileId)
            .orElseThrow(()-> new RuntimeException("could not find resume in database"));

        String fileName = resume.getUpgradedResumeFileName();
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
            .bucket(resume_bucket)
            .key(fileName)
            .build();

        GetObjectPresignRequest presignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10)) 
                        .getObjectRequest(getObjectRequest)
                        .build();
//keeps repeating here
        PresignedGetObjectRequest presignedRequest =
                s3Presigner.presignGetObject(presignRequest);

        return presignedRequest.url().toString();
    }


}
