package com.openclassrooms.Project6Test.ModelsTest;

import com.openclassrooms.Project6Test.Models.Transaction;
import com.openclassrooms.Project6Test.Models.TransactionType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransactionTypeTest {

    private int id = 1;

    private String transactionType = "";

    private List<Transaction> transactions = new ArrayList<>();

    private Transaction transaction = new Transaction();

    @Test
    public void parameterizedConstructor() {

        //Act
        TransactionType newTransactionType = new TransactionType(transactionType);

        //Assert
        assertEquals(transactionType, newTransactionType.getTransactionType());
    }

    @Test
    public void setId() {

        //Arrange
        TransactionType newTransactionType = new TransactionType();

        //Act
        newTransactionType.setId(id);

        //Assert
        assertEquals(id, newTransactionType.getId());
    }

    @Test
    public void setTransactionType() {

        //Arrange
        TransactionType newTransactionType = new TransactionType();

        //Act
        newTransactionType.setTransactionType(transactionType);

        //Assert
        assertEquals(transactionType, newTransactionType.getTransactionType());
    }

    @Test
    public void setTransactions() {

        //Arrange
        TransactionType newTransactionType = new TransactionType();

        transactions.add(transaction);

        //Act
        newTransactionType.setTransactions(transactions);

        //Assert
        assertEquals(transactions, newTransactionType.getTransactions());
    }
}
