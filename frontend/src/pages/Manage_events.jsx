import API_BASE from "../config";
import "../styles/Manage_events.css";

import { useState, useEffect } from "react";
import { useNavigate,Link } from "react-router-dom";

/*Renders the list of the user's events as cards with options to edit or delete each one. */
function Manage_events(){

    const [events, setEvents] = useState([]);
    const navigate = useNavigate();
    const userId = Number(localStorage.getItem("userId"));
    const[openMenuId, setOpenMenuId] = useState(null);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [deleteEventId, setDeleteEventId] = useState(null);

    /*Formats a time string into a 12-hour AM/PM format. */
    const formatTime = (time) =>{
        return new Date(`1970-01-01T${time}`).toLocaleTimeString("en-US", {
        hour: "numeric",
        minute: "2-digit",
        hour12: true
        });
    };

    /*Sends a DELETE request for the selected event and removes it from the local list. */
    const handleDelete = async () =>{
        try{
            const response = await fetch(`${API_BASE}/api/events/${deleteEventId}`,{
                method: "DELETE"
            });

            if(response.ok){
                setShowDeleteModal(false);
                setEvents(prev=>prev.filter((event)=>event.id!==deleteEventId));
                return;
            }


        }catch(error){
            console.error("Error deleting event:", error);
        }
    }

    /*Formats a date string into a human-readable long date. */
    const formatDate = (date) =>{
        return new Date(date).toLocaleDateString("en-US", {
        year: "numeric",
        month: "long",
        day: "numeric"});
    };


    /*Fetches all events for the current user from the backend on mount. */
    useEffect(()=>{
        const fetchEvents = async ()=>{
            try{
                const response = await fetch(`${API_BASE}/api/events/get_events/${userId}`);
                const data = await response.json();
                setEvents(data);
            }
            catch(error){
                console.error("Error fetching events:", error);
            }
        }
        fetchEvents();
    },[userId]);




    return(
        <div className="background">
            <div className="page_content">
            <h1>Manage Events</h1>

                <div className="event_card_container">
                    {events.map((event) =>(
                                <div className="event_card" key={event.id}>
                                    <h2>{event.title}</h2>
                                    <div className="content">

                                        <div className="event_left_side">
                                            <span>🗓️{formatDate(event.date)} at {formatTime(event.startTime)}</span>
                                            <span>📍{event.location}</span>
                                        </div>
                                        {event.notes && (
                                            <div className="event_right_side">
                                            <span className="notes_icon">📝</span>
                                            <span className="notes">{event.notes}</span>
                                        </div>
                                        )}


                                        <div className="event_actions_container">
                                                <button type="button" className="kebab_button" onClick={()=> setOpenMenuId(openMenuId === event.id ? null : event.id)}>
                                                ⋮
                                                </button>
                                                {openMenuId === event.id &&(
                                                    <div className="kebab_events_dropdown">
                                                        <button className="edit_event" type="button" onClick={()=>navigate(`/Edit_event/${event.id}`)}>Edit</button>
                                                        <button className="delete_event" type="button" onClick={()=> {setShowDeleteModal(true); setDeleteEventId(event.id);}}>Delete</button>
                                                    </div>
                                                )}
                                        </div>
                                    </div>


                                </div>
                    ))}
                </div>

                {showDeleteModal &&(
                    <div className="delete_modal">
                        <div className="delete_modal_content">
                            <span>Are you sure you want to delete this event?</span>
                            <div className="delete_modal_actions">
                                <button type="button" className="delete_modal_buttons" onClick={()=>handleDelete()}>Yes</button>
                                <button type="button" className="delete_modal_buttons" onClick ={()=> setShowDeleteModal(false)}>No</button>
                            </div>
                        </div>
                    </div>
                )}



           </div>

        </div>
    );
}
export default Manage_events;

