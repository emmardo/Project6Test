package com.openclassrooms.Project6Test.Models;

public class TransactionDTO {

    private String connectionEmail;
    private String description;
    private float amount;

    public TransactionDTO() {

        this.connectionEmail = "";
        this.description = "";
        this.amount = 0.0f;
    }

    public TransactionDTO(String connectionEmail, String description, float amount) {

        this.connectionEmail = connectionEmail;
        this.description = description;
        this.amount = amount;
    }

    public String getConnectionEmail() {
        return connectionEmail;
    }

    public void setConnectionEmail(String connectionEmail) {
        this.connectionEmail = connectionEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
