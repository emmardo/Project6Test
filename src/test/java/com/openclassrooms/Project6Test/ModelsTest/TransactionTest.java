package com.openclassrooms.Project6Test.ModelsTest;

import com.openclassrooms.Project6Test.Models.*;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TransactionTest {

    private int id = 1;

    private TransactionType transactionType = new TransactionType();

    private Account account = new Account();

    private Connection connection = new Connection();

    private Iban iban = new Iban();

    private float sendersBalanceBeforeTransaction = 1f;

    private float receiversBalanceBeforeTransaction = 1f;

    private float moneyAmount = 1f;

    private Date madeAt = new Date();

    private String origin = "";

    private String description = "";

    @Test
    public void parameterizedConstructor() {

        //Act
        Transaction newTransaction = new Transaction(transactionType, account, moneyAmount, madeAt);

        //Assert
        assertEquals(transactionType, newTransaction.getTransactionType());
        assertEquals(account, newTransaction.getAccount());
        assertEquals(madeAt, newTransaction.getMadeAt());
    }

    @Test
    public void setId() {

        //Arrange
        Transaction newTransaction = new Transaction();

        //Act
        newTransaction.setId(id);

        //Assert
        assertEquals(id, newTransaction.getId());
    }

    @Test
    public void setTransactionType() {

        //Arrange
        Transaction newTransaction = new Transaction();

        //Act
        newTransaction.setTransactionType(transactionType);

        //Assert
        assertEquals(transactionType, newTransaction.getTransactionType());
    }

    @Test
    public void setAccount() {

        //Arrange
        Transaction newTransaction = new Transaction();

        //Act
        newTransaction.setAccount(account);

        //Assert
        assertEquals(account, newTransaction.getAccount());
    }

    @Test
    public void setConnection() {

        //Arrange
        Transaction newTransaction = new Transaction();

        //Act
        newTransaction.setConnection(connection);

        //Assert
        assertEquals(connection, newTransaction.getConnection());
    }

    @Test
    public void setIban() {

        //Arrange
        Transaction newTransaction = new Transaction();

        //Act
        newTransaction.setIban(iban);

        //Assert
        assertEquals(iban, newTransaction.getIban());
    }

    @Test
    public void setMadeAt() {

        //Arrange
        Transaction newTransaction = new Transaction();

        //Act
        newTransaction.setMadeAt(madeAt);

        //Assert
        assertEquals(madeAt, newTransaction.getMadeAt());
    }

    @Test
    public void setOrigin() {

        //Arrange
        Transaction newTransaction = new Transaction();

        //Act
        newTransaction.setOrigin(origin);

        //Assert
        assertEquals(origin, newTransaction.getOrigin());
    }

    @Test
    public void setDescription() {

        //Arrange
        Transaction newTransaction = new Transaction();

        //Act
        newTransaction.setDescription(description);

        //Assert
        assertEquals(description, newTransaction.getDescription());
    }
}
