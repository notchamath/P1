import React from 'react';
import Navbar from '../components/Navbar'; // Adjust the path based on your project structure
import { store } from '../globalData/store';

const HomePage: React.FC = () => {
  // Replace 'Employee' with the actual user type, e.g., 'Manager' or 'Employee'
  const role = store.loggedInUser.role; 
  const username = store.loggedInUser.username;
  let x = ""
  if (role == 'manager') {
    x = "view and manage all users and their reimbursements.";
  } else {
    x ='submit, view, and update descriptions of your own reimbursements'
  }


  return (
    <div>
      <Navbar/>
      <div className="home-content">
        <h1>Welcome to the Employement ERS, {username}! </h1>
        <h2> As {role}, you get to {x}</h2>
        {/* Add other content for your home page here */}
      </div>
    </div>
  );
};

export default HomePage;