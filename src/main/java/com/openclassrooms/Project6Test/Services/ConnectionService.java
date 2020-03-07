package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.Connection;
import com.openclassrooms.Project6Test.Repositories.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {


    private ConnectionRepository connectionRepository;

    @Autowired
    public ConnectionService(ConnectionRepository connectionRepository) {

        this.connectionRepository = connectionRepository;
    }

    private Connection getConnectionByEmail(String email) {

        return connectionRepository.findConnectionByUserEmail(email);
    }


    /*private List<Connection> getAllConnections() {

        return connectionRepository.findAll();
    }


    private List<Connection> getAllConnectionsByType(String connectionType) {

        return connectionRepository.findAll().stream()
                .filter(c -> c.getConnectionType().getConnectionType().equals(connectionType))
                .collect(Collectors.toList());
    }*/
}
