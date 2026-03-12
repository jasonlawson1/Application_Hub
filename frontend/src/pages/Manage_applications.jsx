import "../styles/Manage_applications.css";
import { useState, useEffect } from "react";
import { useNavigate,Link } from "react-router-dom";

function Manage_applications(){
    const navigate = useNavigate();
    const userId = localStorage.getItem("userId");
    const [applications, setApplications] = useState([]);

    useEffect(()=>{
        const fetchApplications = async ()=>{
            try{
                const response = await fetch(`http://localhost:8081/api/applications/user/${userId}`);
                const data = await response.json();
                setApplications(data);
            }
            catch(error){
                console.log(error);
            }
        };

        fetchApplications();
    },[userId]);

    return(
        <div className="Manage_applications_background">
            <h1>Manage applications</h1>
             <div className="quick_actions_box">
                <button type="button" className="quick_actions_btn"  onClick={() => navigate("/Add_application")}>Add new application.</button>
            </div>

            <div className="filter_box">
                <h2>Filter by status:</h2>
                    <select
                        name="filter_status_dropdown"
                        className="filter_status_dropdown"
                    >
                        <option value="All">All</option>
                        <option value="Applied">Applied</option>
                        <option value="Interview">Interview</option>
                        <option value="Offered">Offered</option>
                        <option value="Rejected">Rejected</option>
                    </select>
            </div>

            <div className="table_box_background">
                <div className="table_box">
                    <tbody>
                        {applications.map((app)=>(
                            <tr key={app.id}>
                                <td>{app.status}</td>
                                <td>{app.company}</td>
                                <td>{app.jobTitle}</td>
                                <td>{app.location}</td>
                                <td>{app.dateApplied}</td>
                                <td>{app.deadline}</td>
                                <td>{app.notes}</td>
                               
                            </tr>
                        ))}
                    </tbody>
                </div>
            </div>

        </div>

    )
}export default Manage_applications;