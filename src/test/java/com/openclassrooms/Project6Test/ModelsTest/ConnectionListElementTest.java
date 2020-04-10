package com.openclassrooms.Project6Test.ModelsTest;

import com.openclassrooms.Project6Test.Models.Account;
import com.openclassrooms.Project6Test.Models.Connection;
import com.openclassrooms.Project6Test.Models.ConnectionListElement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConnectionListElementTest {

    private int id = 1;

    private Account account = new Account();

    private Connection connection = new Connection();

    @Test
    public void parameterizedConstructor() {

        //Act
        ConnectionListElement newConnectionListElement = new ConnectionListElement(account, connection);

        //Assert
        assertEquals(account, newConnectionListElement.getAccount());
        assertEquals(connection, newConnectionListElement.getConnection());
    }

    @Test
    public void setId() {

        //Arrange
        ConnectionListElement newConnectionListElement = new ConnectionListElement();

        //Act
        newConnectionListElement.setId(id);

        //Assert
        assertEquals(id, newConnectionListElement.getId());
    }

    @Test
    public void setAccount() {

        //Arrange
        ConnectionListElement newConnectionListElement = new ConnectionListElement();

        //Act
        newConnectionListElement.setAccount(account);

        //Assert
        assertEquals(account, newConnectionListElement.getAccount());
    }

    @Test
    public void setConnection() {

        //Arrange
        ConnectionListElement newConnectionListElement = new ConnectionListElement();

        //Act
        newConnectionListElement.setConnection(connection);

        //Assert
        assertEquals(connection, newConnectionListElement.getConnection());
    }
}
