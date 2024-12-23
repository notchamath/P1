import React, { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import axios from "axios";
import { store } from "../globalData/store";

interface CreateReimbursementProps {
  onReimbursementCreated: () => void;
}

const CreateReimbursement: React.FC<CreateReimbursementProps> = ({ onReimbursementCreated }) => {
  const [showModal, setShowModal] = useState(false);
  const [description, setDescription] = useState<string>("");
  const [amount, setAmount] = useState<number | null>(null);

  const loggedInUserId: number = store.loggedInUser.userId;

  const handleCreateReimbursement = async () => {
    if (description && amount !== null) {
      try {
        const newReimbursement = {
          description,
          amount,
          userId: loggedInUserId,
          status: "pending",
        };
        await axios.post("http://localhost:4444/reimbursements", newReimbursement, {
          withCredentials: true,
          headers: {
            'Content-Type': 'application/json',
          },
        });
        console.log("Reimbursement created successfully!");
        alert("Reimbursement created successfully!");
        setShowModal(false); // Close the modal after creation

        onReimbursementCreated();
      } catch (error) {
        console.error("There was an error creating the reimbursement!", error);
      }
    } else {
      alert("Please fill out both fields.");
    }
  };

  const handleClose = () => setShowModal(false);
  const handleShow = () => setShowModal(true);

  return (
    <>
      <Button variant="primary" onClick={handleShow}>
        Create Reimbursement
      </Button>

      <Modal show={showModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Create Reimbursement</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="description">
              <Form.Label>Description</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />
            </Form.Group>

            <Form.Group controlId="amount" className="mt-3">
              <Form.Label>Amount ($)</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter amount"
                value={amount !== null ? amount : ""}
                onChange={(e) => setAmount(parseFloat(e.target.value))}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Cancel
          </Button>
          <Button variant="primary" onClick={handleCreateReimbursement}>
            Create
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default CreateReimbursement;
