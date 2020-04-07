package com.openclassrooms.Project6Test.Models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "iban", catalog = "pay_my_buddy")
@EntityListeners(AuditingEntityListener.class)
public class Iban {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iban_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_account_id")
    /*@Column(name = "fk_account_id")*/
    private Account account;

    @NotBlank
    @Column(name = "iban")
    private String ibanString;

    @OneToMany(mappedBy = "iban")
    private List<Transaction> transactions;

    public Iban() {
    }

    public Iban(Account account, String ibanString) {

        this.account = account;
        this.ibanString = ibanString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getIbanString() {
        return ibanString;
    }

    public void setIbanString(String iban) {
        this.ibanString = ibanString;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
