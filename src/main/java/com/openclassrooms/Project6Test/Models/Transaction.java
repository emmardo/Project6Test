package com.openclassrooms.Project6Test.Models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "transaction", catalog = "pay_my_buddy")
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_transaction_type_id")
    /*@Column(name = "fk_transaction_type_id")*/
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_account_id")
    /*@Column(name = "fk_account_id")*/
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_connection_id")
    /*@Column(name = "fk_connection_id")*/
    private Connection connection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_iban_id")
    /*@Column(name = "fk_iban_id")*/
    private Iban iban;

    @NotBlank
    @Column(name = "senders_balance_before_transaction")
    private float sendersBalanceBeforeTransaction;

    @Column(name = "receivers_balance_before_transaction")
    private float receiversBalanceBeforeTransaction;

    @NotBlank
    @Column(name = "money_variation")
    private float moneyAmount;

    @NotBlank
    @Column(name = "made_at")
    private Date madeAt;

    private String origin;

    public Transaction() {
    }

    public Transaction(TransactionType transactionType, Account account, float moneyAmount, Date madeAt) {

        this.transactionType = transactionType;
        this.account = account;
        this.moneyAmount = moneyAmount;
        this.madeAt = madeAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Iban getIban() {
        return iban;
    }

    public void setIban(Iban iban) {
        this.iban = iban;
    }

    public float getSendersBalanceBeforeTransaction() {
        return sendersBalanceBeforeTransaction;
    }

    public void setSendersBalanceBeforeTransaction(float sendersBalanceBeforeTransaction) {
        this.sendersBalanceBeforeTransaction = sendersBalanceBeforeTransaction;
    }

    public float getReceiversBalanceBeforeTransaction() {
        return receiversBalanceBeforeTransaction;
    }

    public void setReceiversBalanceBeforeTransaction(float receiversBalanceBeforeTransaction) {
        this.receiversBalanceBeforeTransaction = receiversBalanceBeforeTransaction;
    }

    public float getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(float moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public Date getMadeAt() {
        return madeAt;
    }

    public void setMadeAt(Date madeAt) {
        this.madeAt = madeAt;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
