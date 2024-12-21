package com.revature.models.DTOs;

public class IncomingReimbDTO {

    private String description;
    private double amount;
    private String status = "pending";
    private Integer userId;

    public IncomingReimbDTO() {
    }

    public IncomingReimbDTO(String description, double amount, String status, int userId) {
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.userId = userId;
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
