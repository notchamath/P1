import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Navbar from "./Navbar";
import { ReimbursementInterface } from '../interfaces/ReimbursementInterface';
import { store } from '../globalData/store';
import { Modal, Button } from "react-bootstrap";
import CreateReimbursement from "./createReimbursement";

const MyPendingReimbursements: React.FC = () => {
  const [reimbursements, setReimbursements] = useState<ReimbursementInterface[]>([]);
  const [editingReimbId, setEditingReimbId] = useState<number | null>(null);
  const [newDescription, setNewDescription] = useState<string>("");
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [reimbIdToDelete, setReimbIdToDelete] = useState<number | null>(null);
  const navigate = useNavigate();
  const loggedInUserId: number = store.loggedInUser.userId;

  useEffect(() => {
    getMyReimbursements();
  }, []);

  const getMyReimbursements = async () => {
    try {
      const response = await axios.get(`http://localhost:4444/users/${loggedInUserId}/reimbursements?status=pending`, {
        withCredentials: true,
      });
      console.log(response.data);
      const myReimbursements = response.data.filter(
        (reimbursement: ReimbursementInterface) => reimbursement.userId === loggedInUserId
      );
      setReimbursements(myReimbursements);
    } catch (error) {
      console.error("There was an error fetching the reimbursements!", error);
    }
  };

  const handleEditDescription = (reimbId: number, currentDescription: string) => {
    setEditingReimbId(reimbId);
    setNewDescription(currentDescription);
  };

  const handleUpdateDescription = async () => {
    if (editingReimbId && newDescription) {
      try {
        await axios.patch(`http://localhost:4444/reimbursements/${editingReimbId}/description`,
          { "descriptionText": newDescription}, // Sending the string directly as the request body
          {
            withCredentials: true,
            headers: {
              'Content-Type': 'application/json', // Set the Content-Type to plain text
            },
          }
        );
        console.log(`Reimbursement ID ${editingReimbId} description updated to ${newDescription}`);
        getMyReimbursements();
        setEditingReimbId(null);
      } catch (error) {
        console.error("There was an error updating the description!", error);
      }
    }
  };

  const handleDeleteReimbursement = (reimbId: number) => {
    setReimbIdToDelete(reimbId);
    setShowDeleteModal(true);
  };

  const confirmDeleteReimbursement = async () => {
    if (reimbIdToDelete) {
      try {
        await axios.delete(`http://localhost:4444/reimbursements/${reimbIdToDelete}`, {
          withCredentials: true,
        });
        console.log(`Reimbursement ID ${reimbIdToDelete} deleted`);
        getMyReimbursements();
        setShowDeleteModal(false);
      } catch (error) {
        console.error("There was an error deleting the reimbursement!", error);
      }
    }
  };

  const handleCloseDeleteModal = () => setShowDeleteModal(false);

  return (
    <div>
      <Navbar />
      <div className="container mt-4">
        <h2 className="text-center">List of My Pending Reimbursements</h2>
        <div className="text-center mb-3"> {/* Centering within the container */}
          <CreateReimbursement onReimbursementCreated={getMyReimbursements} />
        </div>
        <table className="table table-striped table-bordered">
          <thead>
            <tr>
              <th>Id</th>
              <th>Description</th>
              <th>Amount</th>
              <th>Status</th>
              <th>User Id</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {reimbursements.map((reimbursement) => (
              <tr key={reimbursement.reimbId}>
                <td>{reimbursement.reimbId}</td>
                <td>
                  {editingReimbId === reimbursement.reimbId ? (
                    <input
                      type="text"
                      value={newDescription}
                      onChange={(e) => setNewDescription(e.target.value)}
                    />
                  ) : (
                    reimbursement.description
                  )}
                </td>
                <td>{reimbursement.amount}</td>
                <td>{reimbursement.status}</td>
                <td>{reimbursement.userId}</td>
                <td>
                  {editingReimbId === reimbursement.reimbId ? (
                    <>
                      <button
                        className="btn btn-success"
                        onClick={handleUpdateDescription}
                      >
                        Save
                      </button>
                      <button
                        className="btn btn-secondary"
                        onClick={() => setEditingReimbId(null)}
                      >
                        Cancel
                      </button>
                    </>
                  ) : (
                    <>
                     <button
                        className="btn btn-primary me-2"
                        onClick={() => handleEditDescription(reimbursement.reimbId!, reimbursement.description)}
                      >
                        Edit
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
      <Modal show={showDeleteModal} onHide={handleCloseDeleteModal}>
        <Modal.Header closeButton>
          <Modal.Title>Confirm Delete</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Are you sure you want to delete reimbursement with ID: {reimbIdToDelete}?
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseDeleteModal}>
            Cancel
          </Button>
          <Button variant="danger" onClick={confirmDeleteReimbursement}>
            Delete
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default MyPendingReimbursements;
