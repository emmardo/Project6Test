package com.openclassrooms.Project6Test.Models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "account_status", catalog = "pay_my_buddy")
@EntityListeners(AuditingEntityListener.class)
public class AccountStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_status_id")
    private int id;

    /*4 Status: Active(1), Inactive(2), NotYetActivated(3) and Deactivated(4)*/
    /*@Column(name = "account_status")*/
    private String accountStatus;

    @OneToMany(mappedBy = "accountStatus")
    private List<Account> accounts;

    public AccountStatus() {
    }

    public AccountStatus(String accountStatus) {

        this.accountStatus = accountStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
