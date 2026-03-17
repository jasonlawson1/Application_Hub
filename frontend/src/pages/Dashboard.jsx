import { useState, StyleSheet } from "react";
import Paper from '@mui/material/Paper';
import Card from '@mui/material/Card';
import Button from "@mui/material/Button";
import { useNavigate,Link } from "react-router-dom";
import "../styles/Dashboard.css";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";


function Dashboard() {
    const first_name = localStorage.getItem("first_name");
    const [events, setEvents] = useState([]);
    const navigate = useNavigate();

    const appliedCount = 0 //Query here
    const responseCount = 0 //Query here
    const interviewCount = 0 //Query here
    const offerCount = 0 //Query here

    const handleDateClick = (info) => {
        const title = prompt("Enter event name:");
        if (title) {
        setEvents([
            ...events,
            { title, date: info.dateStr }
        ]);
        }
    };

    return(
        <div style={{ //abstract into theme.screen
            display: 'flex',
            flexDirection: 'column',
            padding: 8,
            alignItems: 'center'
        }}>
            <h1 style={{}}>
                Application Hub
            </h1>
            <h2 style={{}}>
                Welcome, {first_name}
            </h2>
            
            <div style={{
                justifyContent: 'center'
            }}>
                <Button variant={'contained'} onClick={() => navigate('/Add_application')}>
                    New Application
                </Button>
                <Button variant={'contained'} onClick={() => navigate('/Add_event')}>
                    New Event
                </Button>
            </div>
        
            <div style={{
                display: 'flex',
                flexDirection: 'row',
                width: '100%'
            }}>
                <Card variant={'elevation'} elevation={1} style={{width: '50%'}} 
                onClick={() => navigate('/applications')}>
                    <h3>Your applications</h3>
                    <hr/>
                    <p> View and manage your applications</p>
                </Card>

                <Card variant={'elevation'} elevation={1} style={{width: '50%'}} 
                onClick={() => navigate('/upcoming_interviews')}>
                    <h3>Your interviews</h3>
                    <hr/>
                    <p> View and manage your interviews</p>
                </Card>
            </div>

            <div style={{
                display: 'flex',
                flexDirection: 'row'
            }}>
                
                <Paper variant={'elevation'} elevation={1} style={{width: '50%'}}>
                    <h3>Your Calendar</h3> 
                    <hr/>
                    <FullCalendar
                        plugins={[dayGridPlugin, interactionPlugin]}
                        initialView="dayGridMonth"
                        height="100%"
                        contentHeight="auto"
                        events={events}
                        dateClick={handleDateClick}
                    />
                </Paper>

                <Card variant={'elevation'} elevation={1} style={{width: '50%'}}>
                    <h3>Upcoming Events</h3>
                    <hr/>
                    <p>(list interviews and events here)</p>
                </Card>
            </div>

            <Card variant={'elevation'} elevation={1} style={{width: '75%'}}>
                <h3>glance stats</h3> {/*placeholder; change name later*/}
                <hr/>
                <div style={{display: 'flex', flexDirection: 'row'}}>
                    <Card variant={'elevation'} elevation={1} style={{width: '25%'}}>
                        <h3> Applied </h3>
                        <h4> {appliedCount} </h4>
                    </Card>

                    <Card variant={'elevation'} elevation={1} style={{width: '25%'}}>
                        <h3> Responses </h3>
                        <h4> {responseCount} </h4>
                    </Card>

                    <Card variant={'elevation'} elevation={1} style={{width: '25%'}}>
                        <h3> Interviews </h3>
                        <h4> {interviewCount} </h4>
                    </Card>

                    <Card variant={'elevation'} elevation={1} style={{width: '25%'}}>
                        <h3> Offers </h3>
                        <h4> {offerCount} </h4>
                    </Card>
                </div>
            </Card>
        </div>

    )
}
export default Dashboard;