package com.revature.models.DTOs;

public class OutgoingReimbDTO {

    private Integer reimbId;
    private String description;
    private double amount;
    private String status = "pending";
    private Integer userId;

    public OutgoingReimbDTO() {
    }

    public OutgoingReimbDTO(Integer reimbId, String description, double amount, String status, int userId) {
        this.reimbId = reimbId;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.userId = userId;
    }

    public Integer getReimbId() {
        return reimbId;
    }

    public void setReimbId(Integer reimbId) {
        this.reimbId = reimbId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
