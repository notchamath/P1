import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../App.css';  // Import the CSS file
import axios from 'axios';

const CreateAccount: React.FC = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [role, setRole] = useState("employee");  // Default role is set to "employee"

  const navigate = useNavigate();

  const storeValues = (input: any) => {
    const { name, value } = input.target;

    if (name === "username") {
      setUsername(value);
    } else if (name === "password") {
      setPassword(value);
    } else if (name === "confirm") {
      setConfirmPassword(value);
    } else if (name === "first-name") {
      setFirstName(value);
    } else if (name === "last-name") {
      setLastName(value);
    } else if (name === "role") {
      setRole(value);
    }
  };
  
  const handleCreateAccount = async () => {
    console.log(password)
    console.log(confirmPassword)
    // Basic validation
    if (password !== confirmPassword) {
      alert("Passwords do not match!");
      return;
    }

    if (!username || !password || !firstName || !lastName) {
      alert("Please fill out all required fields!");
      return;
    }

    if (username.length < 6) {
      alert("Please create a username with more than 5 characters");
      return;
    }

    // Create the user object
    const newUser = {
      username,
      password,
      firstName,
      lastName,
      role
    };

    try {
      // Send a POST request to the backend
      const response = await axios.post("http://localhost:4444/users", newUser);
      if (response.status === 201) { // Assuming 201 Created is the success status code
        alert("Account created successfully! Please log in to use your account");
        navigate("/login");
      }
    } catch (error) {
      console.error("Error creating account:", error);
      alert("There was an issue creating your account. Please try again.");
    }
  };


  return (
    <div className="form-container">
      <h2>Welcome to Employee Reimbursement Program</h2>
      <h2>Create Account </h2>
      <form>
        
        <div className="form-group">
          <label htmlFor="username">Username *</label>
          <input type="username" id="username" onChange={storeValues} name="username" placeholder="Enter your username" />
        </div>
        
        <div className="form-group">
          <label htmlFor="password">Password *</label>
          <input type="password" id="password" onChange={storeValues} name="password" placeholder="Enter your password" />
        </div>
        
        <div className="form-group">
          <label htmlFor="confirm-password">Confirm Password *</label>
          <input type="password" id="confirm-password" onChange={storeValues} name="confirm" placeholder="Confirm your password" />
        </div>
        
        <div className="form-group">
            <label htmlFor="first-name">First Name *</label>
            <input type="text" id="first-name" onChange={storeValues} name="first-name" placeholder="Enter your first name" />
        </div>
        
        <div className="form-group">
            <label htmlFor="last-name">Last Name *</label>
            <input type="text" id="last-name" onChange={storeValues} name="last-name" placeholder="Enter your last name" />
        </div>
        
        <div className="form-group">
            <label htmlFor="role">Role</label>
            <input type="text" id="role" onChange={storeValues} name="role" placeholder="Enter your role" />
        </div>

        <div className="form-group">
          <button id="createAccount" type="button" onClick={handleCreateAccount}>Create Account</button>
        </div>

        <div className="form-group link">
          <span>Already have an account? <Link to="/login">Login</Link></span>
        </div>
      </form>
    </div>
  );
};

export default CreateAccount;
