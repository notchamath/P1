import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Navbar from "./Navbar";
import { UserInterface } from '../interfaces/UserInterface';
import { Modal, Button } from "react-bootstrap";
import { store } from "../globalData/store";

const ListUsers: React.FC = () => {
  const [users, setUsers] = useState<UserInterface[]>([]);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [selectedUserId, setSelectedUserId] = useState<number | null>(null);
  const [selectedRole, setSelectedRole] = useState<string | null>(null);
  const [loggedInUserId, setLoggedInUserId] = useState<number | null>(null); // Store the logged-in user's ID
  const navigate = useNavigate();

  const user = store.loggedInUser.role;
  if (user !== 'manager') { 
    useEffect(() => {
      navigate("/home");
    }, [navigate])
  }

  useEffect(() => {
    getAllUsers();
    // getLoggedInUser(); // Fetch the logged-in user's ID
  }, []);

  const getAllUsers = async () => {
    try {
      const response = await axios.get("http://localhost:4444/users", {
        withCredentials: true,
      });
      console.log(response.data);
      setUsers(response.data);
    } catch (error) {
      console.error("There was an error fetching the users!", error);
    }
  };

  // const getLoggedInUser = async () => {
  //   try {
  //     const response = await axios.get("http://localhost:4444/users/loggedInUser", {
  //       withCredentials: true,
  //     });
  //     setLoggedInUserId(response.data.userId);
  //   } catch (error) {
  //     console.error("There was an error fetching the logged-in user!", error);
  //   }
  // };

  const handleDeleteUser = (userId: number) => {
    setSelectedUserId(userId);
    setShowDeleteModal(true);
  };

  const confirmDeleteUser = async () => {
    if (selectedUserId) {
      try {
        await axios.delete(`http://localhost:4444/users/${selectedUserId}`, {
          withCredentials: true,
        });
        console.log(`User ID ${selectedUserId} deleted`);
        getAllUsers(); // Refresh the list
        setShowDeleteModal(false);
      } catch (error) {
        console.error("There was an error deleting the user!", error);
      }
    }
  };

  const handleUpdateRole = (userId: number | undefined, role: string) => {

    if(typeof userId === "number"){

      setSelectedUserId(userId);
      setSelectedRole(role);
      setShowUpdateModal(true);
    }
  };

  const updateRole = async (role: string) => {
    if (selectedUserId) {
      try {
        await axios.patch(
          `http://localhost:4444/users/${selectedUserId}/role`,
          {"role": role}, // Sending the new role as a plain string
          {
            withCredentials: true,
            headers: {
              'Content-Type': 'application/json', // Setting the Content-Type to plain text
            },
          }
        );
        console.log(`User ID ${selectedUserId} role updated to ${role}`);
        getAllUsers(); // Refresh the list
        setShowUpdateModal(false);
      } catch (error) {
        console.error("There was an error updating the role!", error);
      }
    }
  };

  const handleClose = () => {
    setShowDeleteModal(false);
    setShowUpdateModal(false);
  };

  return (
    <div>
      <Navbar/> {/* Assuming "manager" for example */}
      <div className="container mt-4">
        <h2 className="text-center">List of Users</h2>
        <table className="table table-striped table-bordered">
          <thead>
            <tr>
              <th>Id</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Username</th>
              <th>Role</th>
              <th>Actions</th> {/* New column for actions */}
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.userId}>
                <td>{user.userId}</td>
                <td>{user.firstName}</td>
                <td>{user.lastName}</td>
                <td>{user.username}</td>
                <td>{user.role}</td>
                <td>
                  {/* Conditionally render buttons only if it's not the logged-in user */}
                  {user.userId !== loggedInUserId && (
                    <>
                      <button
                        className="btn btn-danger me-2"
                        onClick={() => handleDeleteUser(user.userId!)}
                      >
                        Delete
                      </button>
                      <button
                        className="btn btn-primary"
                        onClick={() => handleUpdateRole(user.userId, user.role)}
                      >
                        Update Role
                      </button>
                    </>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Modal for confirming delete */}
      <Modal show={showDeleteModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Confirm Delete</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Are you sure you want to delete user with ID: {selectedUserId}?
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Cancel
          </Button>
          <Button variant="danger" onClick={confirmDeleteUser}>
            Delete
          </Button>
        </Modal.Footer>
      </Modal>

      {/* Modal for updating role */}
      <Modal show={showUpdateModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Update Role</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <p>Select a new role for user with ID: {selectedUserId}</p>
          <div className="d-flex justify-content-between">

            {selectedRole==="employee"?
            <Button variant="primary" onClick={() => updateRole("manager")}>
              Manager
            </Button>:
            <Button variant="primary" onClick={() => updateRole("employee")}>
              Employee
            </Button>
            } 
            <Button variant="secondary" onClick={handleClose}>
              Cancel
            </Button>
          </div>
        </Modal.Body>
      </Modal>
    </div>
  );
};

export default ListUsers;
