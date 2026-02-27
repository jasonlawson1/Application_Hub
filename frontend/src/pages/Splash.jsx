import "../styles/Splash.css";
import { useEffect } from "react";
import gsap from "gsap";
import { useNavigate } from "react-router-dom";

function Splash() {

    const navigate = useNavigate();

    useEffect(() => {
    // Animate X
    gsap.to(".ball", {
      x: 350,
      duration: 3,
      repeat: -1,
      ease: "linear",
    });

    // Animate Y
    gsap.to(".ball", {
      y: -25,
      duration: 0.2,
      repeat: -1,
      yoyo: true,
      ease: "power1.out",
    });

    const timer = setTimeout(() => {
      navigate("/signup"); 
    }, 3500);

    return () => clearTimeout(timer);
  }, [navigate]);


    
  
  return (
    <div className="splash-page">

      <header className="splash">
        <h1>Application Hub</h1>
      </header>

      <div className="loading-container">
        <div className="ball"></div>
      </div>

    </div>
  );
}

export default Splash;