package com.openclassrooms.Project6Test.ModelsTest;

import com.openclassrooms.Project6Test.Models.Connection;
import com.openclassrooms.Project6Test.Models.ConnectionType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ConnectionTypeTest {

    private int id = 1;

    private String connectionType = "";

    private List<Connection> connections = new ArrayList<>();

    private Connection connection = new Connection();

    @Test
    public void parameterizedConstructor() {

        //Act
        ConnectionType newConnectionType = new ConnectionType(connectionType);

        //Assert
        assertEquals(connectionType, newConnectionType.getConnectionType());
    }

    @Test
    public void setId() {

        //Arrange
        ConnectionType newConnectionType = new ConnectionType();

        //Act
        newConnectionType.setId(id);

        //Assert
        assertEquals(id, newConnectionType.getId());
    }

    @Test
    public void setConnectionType() {

        //Arrange
        ConnectionType newConnectionType = new ConnectionType();

        //Act
        newConnectionType.setConnectionType(connectionType);

        //Assert
        assertEquals(connectionType, newConnectionType.getConnectionType());
    }

    @Test
    public void setConnections() {

        //Arrange
        ConnectionType newConnectionType = new ConnectionType();

        connections.add(connection);

        //Act
        newConnectionType.setConnections(connections);

        //Assert
        assertEquals(connections, newConnectionType.getConnections());
    }
}
