import "../styles/Upgrade_resume.css";
import { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { FaCloudUploadAlt } from "react-icons/fa";

function Upgrade_resume() {
    const navigate = useNavigate();
    const fileRef = useRef(null);
    const [step, setStep] = useState(1);
    const [errorMessage, setErrorMessage] = useState("");
    const [resumeUrl, setResumeUrl] = useState("");
    const [isUploading, setIsUploading] = useState(false);
    const userId = Number(localStorage.getItem("userId"));

    const handleFileChange = async (e) => {
        const file = e.target.files[0];
        if (!file) return;

        if (!file.name.endsWith(".docx")) {
            setErrorMessage("Only .docx files allowed");
            return;
        }

        setErrorMessage("");
        setIsUploading(true);

        try {
            const formData = new FormData();
            formData.append("file", file);
            formData.append("userId", userId);

            const response = await fetch("http://localhost:8081/api/upgrade_resume/upload", {
                method: "POST",
                body: formData,
            });

            if (!response.ok) throw new Error("Upload failed");

            const data = await response.json();
            setResumeUrl(data.url);
            setStep(2);
        } catch (error) {
            console.error("Error uploading file:", error);
            setErrorMessage("Upload failed. Please try again.");
        } finally {
            setIsUploading(false);
        }
    };

    return (
        <div className="background">
            <div className="page_content">
                <h1>Upload Your Resume</h1>
                <div className="modal">
                    <div className="modal_content">
                        <button
                            className="close_login_btn"
                            onClick={() => navigate("/Dashboard")}
                        >
                            &times;
                        </button>

                        {step === 1 && (
                            <>
                                <h2>Upload Your Resume</h2>
                                <div className="upload_content">
                                    {errorMessage && <p className="error_message">{errorMessage}</p>}
                                    <FaCloudUploadAlt className="cloud_icon" />
                                    <input
                                        type="file"
                                        ref={fileRef}
                                        style={{ display: "none" }}
                                        accept=".docx"
                                        onChange={handleFileChange}
                                    />
                                    <button
                                        type="button"
                                        className="upload_button"
                                        onClick={() => fileRef.current.click()}
                                        disabled={isUploading}
                                    >
                                        {isUploading ? "Uploading..." : "Upload Resume"}
                                    </button>
                                    <span>Only .docx files allowed</span>
                                </div>
                            </>
                        )}

                        {step === 2 && (
                            <>
                                <h2>Your resume is ready!</h2>
                                <div className="resume_link_container">
                                    <a className="resume_link" href={resumeUrl} download="resume.docx">
                                        <p className="resume_link_text">Download Your Resume</p>
                                    </a>
                                </div>
                            </>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Upgrade_resume;