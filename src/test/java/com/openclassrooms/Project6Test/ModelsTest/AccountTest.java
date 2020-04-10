package com.openclassrooms.Project6Test.ModelsTest;

import com.openclassrooms.Project6Test.Models.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    private int id = 1;

    private User user = new User();

    private AccountType accountType = new AccountType();

    private AccountStatus accountStatus = new AccountStatus();

    private Connection connection = new Connection();

    private float currentBalance = 1f;

    private Transaction transaction = new Transaction();

    private List<Transaction> transactions = new ArrayList<>();

    private Iban iban = new Iban();

    private List<Iban> ibans = new ArrayList<>();

    private ConnectionListElement connectionListElement = new ConnectionListElement();

    private List<ConnectionListElement> connectionListElements = new ArrayList<>();


    @Test
    public void parameterizedConstructor() {

        //Act
        Account newAccount = new Account(user, accountType, accountStatus, connection, currentBalance);

        //Assert
        assertEquals(user, newAccount.getUser());
        assertEquals(accountType, newAccount.getAccountType());
        assertEquals(accountStatus, newAccount.getAccountStatus());
        assertEquals(connection, newAccount.getConnection());
    }

    @Test
    public void setId() {

        //Arrange
        Account newAccount = new Account();

        //Act
        newAccount.setId(id);

        //Assert
        assertEquals(id, newAccount.getId());
    }

    @Test
    public void setUser() {

        //Arrange
        Account newAccount = new Account();

        //Act
        newAccount.setUser(user);

        //Assert
        assertEquals(user, newAccount.getUser());
    }

    @Test
    public void setAccountType() {

        //Arrange
        Account newAccount = new Account();

        //Act
        newAccount.setAccountType(accountType);

        //Assert
        assertEquals(accountType, newAccount.getAccountType());
    }

    @Test
    public void setAccountStatus() {

        //Arrange
        Account newAccount = new Account();

        //Act
        newAccount.setAccountStatus(accountStatus);

        //Assert
        assertEquals(accountStatus, newAccount.getAccountStatus());
    }

    @Test
    public void setConnection() {

        //Arrange
        Account newAccount = new Account();

        //Act
        newAccount.setConnection(connection);

        //Assert
        assertEquals(connection, newAccount.getConnection());
    }

    @Test
    public void setTransactions() {

        //Arrange
        Account newAccount = new Account();

        transactions.add(transaction);

        //Act
        newAccount.setTransactions(transactions);

        //Assert
        assertEquals(transactions, newAccount.getTransactions());
    }

    @Test
    public void setIbans() {

        //Arrange
        Account newAccount = new Account();

        ibans.add(iban);

        //Act
        newAccount.setIbans(ibans);

        //Assert
        assertEquals(ibans, newAccount.getIbans());
    }

    @Test
    public void setConnectionListElements() {

        //Arrange
        Account newAccount = new Account();

        connectionListElements.add(connectionListElement);

        //Act
        newAccount.setConnectionListElements(connectionListElements);

        //Assert
        assertEquals(connectionListElements, newAccount.getConnectionListElements());
    }
}
