package com.openclassrooms.Project6Test.Models;

public class WithdrawalDTO {

    private float amount;
    private String account;

    public WithdrawalDTO() {
        this.amount = 0.0f;
        this.account = "";
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
