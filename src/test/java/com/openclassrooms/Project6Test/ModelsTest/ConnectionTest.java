package com.openclassrooms.Project6Test.ModelsTest;

import com.openclassrooms.Project6Test.Models.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ConnectionTest {

    private int id = 1;

    private ConnectionType connectionType = new ConnectionType();

    private User user = new User();

    private List<Transaction> transactions = new ArrayList<>();

    private Transaction transaction = new Transaction();

    @Test
    public void parameterizedConstructor() {

        //Act
        Connection newConnection = new Connection(connectionType, user);

        //Assert
        assertEquals(connectionType, newConnection.getConnectionType());
        assertEquals(user, newConnection.getUser());
    }

    @Test
    public void setId() {

        //Arrange
        Connection newConnection = new Connection();

        //Act
        newConnection.setId(id);

        //Assert
        assertEquals(id, newConnection.getId());
    }

    @Test
    public void setConnectionType() {

        //Arrange
        Connection newConnection = new Connection();

        //Act
        newConnection.setConnectionType(connectionType);

        //Assert
        assertEquals(connectionType, newConnection.getConnectionType());
    }

    @Test
    public void setUser() {

        //Arrange
        Connection newConnection = new Connection();

        //Act
        newConnection.setUser(user);

        //Assert
        assertEquals(user, newConnection.getUser());
    }

    @Test
    public void setTransactions() {

        //Arrange
        Connection newConnection = new Connection();

        transactions.add(transaction);

        //Act
        newConnection.setTransactions(transactions);

        //Assert
        assertEquals(transactions, newConnection.getTransactions());
    }
}
