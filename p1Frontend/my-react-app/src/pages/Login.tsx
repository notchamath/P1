import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../App.css';  // Import the CSS file
import {useState} from "react";
import axios from 'axios';
import { store } from '../globalData/store';

const Login: React.FC = () => {
  const [user, setUser] = useState({
    username:"",
    password:""
  })

  const navigate = useNavigate()

  const storeValues = (input:any) => {
    if (input.target.name === "username") {
      setUser((user) => ({...user, username:input.target.value}));
    } else {
      setUser((user) => ({...user, password:input.target.value}));
    }
  }

  const login = async () => {
    try {
      const response = await axios.post("http://localhost:4444/auth", user, { withCredentials: true });
      console.log(response.data);
      store.loggedInUser = response.data;
  
      // alert("Welcome, " + store.loggedInUser.username);
  
      // Ensure this is called after the response is processed
      
      navigate("/home");

      
    } catch (error) {
      console.error("Login failed!", error);
      alert("Incorrect username or password!");
    }
  };
  


  return (
    <div className="form-container">
      <h2>Welcome to the Employement Reimbursement Program</h2>
      <h2>Login</h2>
      <form>
        <div className="form-group">
          <label htmlFor="username">Username</label>
          <input type="text" id="username" name="username" onChange={storeValues} placeholder="Enter your username" />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input type="password" id="password" name= "password" onChange={storeValues} placeholder="Enter your password" />
        </div>
        <div className="form-group button">
          <button id="login" type="button" onClick={login} >Login</button>
        </div>
        <div className="form-group link">
          <span>Don't have an account? <Link to="/create-account">Create Account</Link></span>
        </div>
      </form>
    </div>
  );
};

export default Login;