import API_BASE from "../config";
import { useState } from "react";
import { useNavigate,Link } from "react-router-dom";
import "../styles/Forgot_password.css";

/*Renders the multi-step forgot password flow for email verification, code entry, and password reset. */
function Forgot_password(){
    const navigate = useNavigate();
    const [instructions, setInstructions]=useState("Enter your email and you will receive a code to reset your password.");
    const[errorMessage, setErrorMessage]=useState("");
    const[changedPasswordMsg, setchangedPasswordMsg]=useState("");
    const [steps, setSteps]= useState(1);
    const[emailForm, setEmailForm]=useState({
        email: ""
    });
    const[codeForm, setCodeForm]=useState({
        code: ""
    });
    const[newPasswordForm, setNewPasswordForm]=useState({
        newPassword:"",
        confirmNewPassword:""
    });

    /*Sends the user's email to the backend to initiate the password reset flow. */
    const handleSendEmail = async (e) => {
            e.preventDefault();
                try{
                    const response = await fetch(`${API_BASE}/api/forgot_password/send_email`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                },
                body: JSON.stringify(emailForm),
                });

                if(response.ok){
                    setSteps(2);
                    setInstructions("Type in the code you received in your email to reset your password.");
                    setErrorMessage("");
                }
                else{
                    setErrorMessage("Something went wrong or your account does not exists.")
                }

                }
                catch(error){
                    console.log(error);
                }
    }

    /*Submits the verification code to the backend and advances to the password reset step. */
    const handleSendCode = async(e) => {
        e.preventDefault();

        try{
                const data = {
                    email: emailForm.email,
                    code: codeForm.code
                };
                const response = await fetch(`${API_BASE}/api/forgot_password/verify_code`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(data),
                });

                const responseData = await response.json();

                if(response.ok){
                    setSteps(3);
                    setInstructions("Type in your new Password");
                     setErrorMessage("");
                }
                else{

                    setErrorMessage(responseData.message)
                }
        }
        catch(error){
            console.log(error);
        }
    }

    /*Validates and submits the new password to the backend to complete the reset. */
    const handleSendNewPassword = async(e) =>{
        e.preventDefault();
        //if new password meets all requirements redirect to success page
        try {

            const data ={
                email: emailForm.email,
                newPassword: newPasswordForm.newPassword,
                confirmNewPassword: newPasswordForm.confirmNewPassword
            }

            if(newPasswordForm.newPassword!==newPasswordForm.confirmNewPassword){
                setErrorMessage("Passwords do not match.")
                return;
            }
            if(!newPasswordForm.newPassword || !newPasswordForm.confirmNewPassword){
                setErrorMessage("Please fill in all fields")
                return;
            }
            setErrorMessage("");

            const response = await fetch(`${API_BASE}/api/forgot_password/change_password`,{
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(data),
            });

            const responseData = await response.json();

            if(response.ok){
                // make a page that says their password has been changed with a link that takes them back to login page
                setSteps(4);
                setInstructions("");
                setchangedPasswordMsg("Your password has been succesfully changed! You may return to the login page.🎉");
            }
            else{
                setErrorMessage(data.message);
            }

        } catch (error) {

        }


    }




    /*Updates the email form state when the user types in the email input. */
    const handleEmail = (e) =>{
        setEmailForm({
            ...emailForm,
            [e.target.name]: e.target.value
        });
    }

    /*Updates the code form state when the user types in the code input. */
    const handleCode = (e) =>{
        setCodeForm({
            ...codeForm,
            [e.target.name]: e.target.value
        });
    }

    /*Updates the new password form state when the user types in either password field. */
    const handleNewPassword = (e)=>{
        setNewPasswordForm({
            ...newPasswordForm,
            [e.target.name]: e.target.value
        });
    }


    return (
    <div className="forgot_password_background">
        <div className="forgot_password_module">
              <button
                    className="close_login_btn"
                    onClick={() => navigate(-1)}
                >
                    &times;
                </button>
            <h1>Forgot password</h1>
            <p className="instructions">{instructions}</p>
            <p className="error_message">{errorMessage}</p>

            {steps === 1 &&(
            <form id="emailForm" onSubmit={handleSendEmail}>
                <>
                    <label htmlFor="email">Email</label>
                    <input
                        type="email"
                        name="email"
                        id="email"
                        placeholder="Enter your email"
                        className="text_box"
                        onChange={handleEmail}
                        value={emailForm.email}
                    />
                </>

                <button type="submit" className="submit_btn" >Submit</button>
                </form>
             )}

           {steps === 2 &&(
              <form id="codeForm" onSubmit={handleSendCode}>

                <>
                <label htmlFor="code">Code</label>
                    <input
                        type="text"
                        name="code"
                        id="code"
                        placeholder="Enter your code"
                        className="text_box"
                        onChange={handleCode}
                        value={codeForm.code}
                    />
                </>
              <button type="submit" className="submit_btn" >Submit</button>
              </form>
            )}

            {steps === 3 &&(
             <form id="newPasswordForm" onSubmit={handleSendNewPassword}>
                    <>
                    <label htmlFor="newPassword">New password</label>
                        <input
                            type="text"
                            name="newPassword"
                            id="newPassword"
                            placeholder="Enter your new password"
                            className="text_box"
                            onChange={handleNewPassword}
                            value={newPasswordForm.newPassword}
                        />
                     <label htmlFor="confirmNewPassword">Confirm new password</label>
                        <input
                            type="text"
                            name="confirmNewPassword"
                            id="confirmNewPassword"
                            placeholder="Re-enter your new password"
                            className="text_box"
                            onChange={handleNewPassword}
                            value={newPasswordForm.confirmNewPassword}
                        />


                    </>
                    <button type="submit" className="submit_btn" >Submit</button>
                </form>
              )}

              {steps === 4 &&(
                <>
                    <p className="changed_password_message">{changedPasswordMsg}</p>
                    <Link to="/Login" className="login_link">
                        <span>Return to login page here.</span>
                    </Link>
                </>

              )}




        </div>
    </div>
);

}
export default Forgot_password;

