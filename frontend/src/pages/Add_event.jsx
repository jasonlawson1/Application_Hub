import { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useParams } from "react-router-dom";
import "../styles/Add_event.css";

function Add_event(){
   const { id, date } = useParams();
   const navigate = useNavigate();
   const userId =  Number(localStorage.getItem("userId"));
   const [errorMessage, setErrorMessage] = useState("");

   const [formData, setFormData] = useState({
    title: "",
    startTime: "",
    location:"",
    notes: "",
    date:""
   
   });

   const handleChange = (e) =>{
      setFormData({...formData, [e.target.name]: e.target.value});
   };

    const formatDate = (date) =>{
        const [year, month, day] = date.split("-");

        return new Date(year, month - 1, day).toLocaleDateString("en-US", {
            year: "numeric",
            month: "long",
            day: "numeric",
        });
   };

    const displayDate = formData.date || date;
   
    useEffect(()=> {
        const editEvent = async ()=>{
            try{
                if(!id)return;
                const response = await fetch(`http://localhost:8081/api/events/${id}`);
                const data = await response.json();
                setFormData({
                    ...data,
                    date: data.date
                });
                
            }
            catch(error){
                console.error("error fetching event:", error);
            }
        };
        editEvent();
    }, [id]);
    
  

   
   
   

   const handleSubmit = async(e)=>{
        e.preventDefault();
        if(!formData.title ||!formData.startTime){
            setErrorMessage("Please fill in title and start time fields.");
            return;
        }
        try {
            const url = id? `http://localhost:8081/api/events/${id}` : `http://localhost:8081/api/events/create_event`;
            const method = id? "PUT" : "POST";
            const response = await fetch(url,{
                method: method,
                headers: {
                "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    ...formData,
                    date: formData.date || date,
                    userId
                })
            });

        

            if(response.ok){
                                //make a list of all events
                const allEvents = JSON.parse(localStorage.getItem("events")) || [];

                if(method === "POST"){
                    //save new event
                     const newEvent = {
                    ...formData,
                    //date: data.date||date,
                    userId: userId
                };

                //update the events
                const updatedEvents = [...allEvents, newEvent];
                 // turns list into string
                localStorage.setItem("events", JSON.stringify(updatedEvents));
                } 
               
                
                const hasSeenEventSuccessPage = (localStorage.getItem("seenEventSuccessPage"));
                if(!hasSeenEventSuccessPage){
                    localStorage.setItem("seenEventSuccessPage", "true");
                    localStorage.setItem("formType", "event")
                    navigate("/Succesfully_submitted");
                }
                else{
                    navigate("/Manage_events");
                }
                
            }


        } catch (error) {
            console.log(error);
        }
      

   }
   

    return(
        
        <div className="background">
            <div className="page_content">
            <h1>Add Event</h1>
            <div className="modal">
                    <p className="date_title">{displayDate ? formatDate(displayDate) : ""}</p>
                    {errorMessage &&<p className="error_message">{errorMessage}</p>}
                    
                    <form id="event_form" className="event_form" onSubmit={handleSubmit}>
                        <label htmlFor ="title">Title</label>
                        <input
                            type="text"
                            name="title"
                            placeholder="Title"
                            id="title"
                            className="text_box"
                            value={formData.title}
                            onChange={handleChange}
                            />
                        <label htmlFor ="location">Location</label>
                        <input
                            type="text"
                            name="location"
                            placeholder="Location"
                            id="location"
                            className="text_box"
                            value={formData.location}
                            onChange={handleChange}
                            />
                        <label htmlFor="startTime">Start time</label>
                        <input
                            type="time"
                            name="startTime"
                            placeholder="Enter the start time"
                            id="startTime"
                            className="text_box"
                            value={formData.startTime}
                            onChange={handleChange}
                            />
                        <label htmlFor="">Notes</label>
                        <textarea
                            id="notes"
                            name="notes"
                            placeholder="Add any notes about the event..."
                            className="notes"
                            value={formData.notes}
                            onChange={handleChange}
                        ></textarea>

                        <div className="save_cancel_container">
                            <button type="button" className="save_cancel_buttons" onClick={()=>navigate("/Dashboard")}>Cancel</button>
                            <button type="submit" className="save_cancel_buttons">Save event</button>
                        </div>
                    </form>
                   

             
                </div>
            </div>
        </div>

    );
}
export default Add_event;