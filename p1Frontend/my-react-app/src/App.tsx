import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';

import Navbar from './components/Navbar';
import Login from './pages/Login';
import CreateAccount from './pages/CreateAccount';
import HomePage from './pages/Home';
import ListUsers from './components/ListUsers';
import ListReimbursements from './components/ListReimbursements';
import PendingReimbursements from './components/PendingReimbursements';
import MyReimbursements from './components/MyReimbursements';
import MyPendingReimbursements from './components/myPendingReimbursements';

const App: React.FC = () => {

  return (
    <Router>
      <div className="container mx-auto mt-4">
        <Routes>
          {/* Redirect to Login by default */}
          <Route path="/" element={<Navigate to="/login" />} />
          {/* Login Page */}
          <Route path="/login" element={<Login />} />
          {/* Create Account Page */}
          <Route path="/create-account" element={<CreateAccount />} />
          {/* Home Page */}
          <Route path="/home" element={<HomePage />} />
          {/* List Users Page */}
          <Route path="/listUsers" element={<ListUsers />} />
          {/* List Reimbursements Page */}
          <Route path="/listReimbursements" element={<ListReimbursements />} />
          {/* Pending Reimbursements Page */}
          <Route path="/pendingReimbursements" element={<PendingReimbursements />} />
          {/* My Reimbursements Page */}
          <Route path="/myReimbursements" element={<MyReimbursements />} />
          {/* My Pending Reimbursements Page */}
          <Route path="/myPendingReimbursements" element={<MyPendingReimbursements />} />

          {/* Add other routes as needed */}
        </Routes>
      </div>
    </Router>
  );
};

export default App;
