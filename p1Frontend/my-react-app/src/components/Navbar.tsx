import React from 'react';
import { Link } from 'react-router-dom';
import "../App.css";
import { store } from '../globalData/store';

const Navbar: React.FC = () => {
    const userType = store.loggedInUser.role; // Get the userType from the global store

    return (
      <nav className="navbar">
        <div className="navbar-container">
          <Link to="/home" className="navbar-brand">
            Employee Reimbursement
          </Link>
          <div className="navbar-links">

            {userType === 'manager' && (
              <>
              <Link to="/listUsers">All Users</Link>
              <Link to="/listReimbursements">All Reimbursements</Link>
              <Link to="/pendingReimbursements">Pending Reimbursements</Link>
              </>
            )}
            
            {userType !== 'manager' && (
              <>
              <Link to="/myReimbursements">My Reimbursements</Link>
              <Link to="/myPendingReimbursements">My Pending Reimbursements</Link>
              </>
            )}

            <Link to="/login">Logout</Link>

          </div>
        </div>
      </nav>
    );
};

export default Navbar;
