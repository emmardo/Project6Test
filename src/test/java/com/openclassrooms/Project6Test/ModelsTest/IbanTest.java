package com.openclassrooms.Project6Test.ModelsTest;

import com.openclassrooms.Project6Test.Models.Account;
import com.openclassrooms.Project6Test.Models.Iban;
import com.openclassrooms.Project6Test.Models.Transaction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IbanTest {

    private int id = 1;

    private Account account = new Account();

    private String iban = "";

    private List<Transaction> transactions = new ArrayList<>();

    private Transaction transaction = new Transaction();

    @Test
    public void parameterizedConstructor() {

        //Act
        Iban newIban = new Iban(account, iban);

        //Assert
        assertEquals(account, newIban.getAccount());
        assertEquals(iban, newIban.getIban());
    }

    @Test
    public void setId() {

        //Arrange
        Iban newIban = new Iban();

        //Act
        newIban.setId(id);

        //Assert
        assertEquals(id, newIban.getId());
    }

    @Test
    public void setAccount() {

        //Arrange
        Iban newIban = new Iban();

        //Act
        newIban.setAccount(account);

        //Assert
        assertEquals(account, newIban.getAccount());
    }

    @Test
    public void setIban() {

        //Arrange
        Iban newIban = new Iban();

        //Act
        newIban.setIban(iban);

        //Assert
        assertEquals(iban, newIban.getIban());
    }

    @Test
    public void setTransactions() {

        //Arrange
        Iban newIban = new Iban();

        transactions.add(transaction);

        //Act
        newIban.setTransactions(transactions);

        //Assert
        assertEquals(transactions, newIban.getTransactions());
    }
}
