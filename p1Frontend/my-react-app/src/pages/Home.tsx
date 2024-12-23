import React, { useEffect } from 'react';
import Navbar from '../components/Navbar'; // Adjust the path based on your project structure
import { store } from '../globalData/store';
import { useNavigate } from 'react-router-dom';

const HomePage: React.FC = () => {
  const navigate = useNavigate();
  const role = store.loggedInUser.role; 
  const username = store.loggedInUser.username;
  let x = ""
  if (role == 'manager') {
    x = "view and manage all users and their reimbursements.";
  } else if (role == 'employee') {
    x ='submit, view, and update descriptions of your own reimbursements'
  } else { 
    useEffect(() => {
      navigate("/login");
    }, [navigate])
  }

  return (
    <div>
      <Navbar/>
      <div className="home-content text-center">
        <h1>Welcome to the Employment ERS, {username}!</h1>
        <h2>As {role}, you get to {x}</h2>
        <img src="/ersLandingPage.jpg" alt="ERS Landing Page" className="img-fluid mt-4" /> {/* Add the landing page image */}
      </div>
    </div>
  );
};

export default HomePage;