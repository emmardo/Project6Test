package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.ConnectionType;
import com.openclassrooms.Project6Test.Repositories.ConnectionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionTypeService {

    @Autowired
    private ConnectionTypeRepository connectionTypeRepository;


    public void createConnectionTypes(List<String> connectionTypeList) {

        connectionTypeRepository.deleteAll();

        for (String connectionType: connectionTypeList) {

            ConnectionType connectionTypeInstance = new ConnectionType(connectionType);

            connectionTypeRepository.save(connectionTypeInstance);
        }
    }

    /*public void addConnectionType(String connectionType) {

        ConnectionType connectionTypeInstance = new ConnectionType(connectionType);

        connectionTypeRepository.save(connectionTypeInstance);
    }*/

    public List<String> getAllConnectionTypes() {

        List<String> connectionTypes = null;

        if(!connectionTypeRepository.findAll().isEmpty()) {

            for (ConnectionType connectionType : connectionTypeRepository.findAll()) {

                connectionTypes.add(connectionType.getConnectionType());
            }
        }

        return connectionTypes;
    }

    /*public String getConnectionTypeById(int connectionTypeId) {

        return connectionTypeRepository.findConnectionTypeById(connectionTypeId).getConnectionType();
    }

    public void deleteConnectionType(String connectionType) {

        connectionTypeRepository.delete(connectionTypeRepository.findConnectionTypeByConnectionType(connectionType));
    }*/
}
