package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.Account;
import com.openclassrooms.Project6Test.Models.Connection;
import com.openclassrooms.Project6Test.Models.ConnectionListElement;
import com.openclassrooms.Project6Test.Repositories.AccountRepository;
import com.openclassrooms.Project6Test.Repositories.ConnectionListElementRepository;
import com.openclassrooms.Project6Test.Repositories.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionListElementService {

    private ConnectionListElementRepository connectionListElementRepository;

    private AccountRepository accountRepository;

    private ConnectionRepository connectionRepository;

    @Autowired
    public ConnectionListElementService(ConnectionListElementRepository connectionListElementRepository,
                                        AccountRepository accountRepository,
                                        ConnectionRepository connectionRepository) {

        this.connectionListElementRepository = connectionListElementRepository;
        this.accountRepository = accountRepository;
        this.connectionRepository = connectionRepository;
    }


    public void createConnectionListElement(String userEmail, String connectionEmail) {

        Account account = accountRepository.findAccountByUserEmail(userEmail);

        Connection connection = connectionRepository.findConnectionByUserEmail(connectionEmail);

        ConnectionListElement connectionListElement = new ConnectionListElement(account, connection);

        connectionListElementRepository.save(connectionListElement);
    }

    public ConnectionListElement getConnectionListElementByConnectionEmail(String userEmail, String connectionEmail) {

        return getConnectionListElementsByUserEmail(userEmail).stream().filter(c -> c.getConnection().getUser()
                .getEmail().equals(connectionEmail)).findFirst().get();
    }

    public List<ConnectionListElement> getConnectionListElementsByUserEmail(String userEmail) {

        return connectionListElementRepository.findConnectionListElementsByAccountUserEmail(userEmail);
    }

    public void updateConnectionListElementsConnection(String oldConnectionEmail, String newConnectionEmail) {

        ConnectionListElement element = connectionListElementRepository
                                        .findConnectionListElementByConnectionUserEmail(oldConnectionEmail);

        element.setConnection(connectionRepository.findConnectionByUserEmail(newConnectionEmail));

        connectionListElementRepository.save(element);
    }

    public void deleteConnectionListElementByConnectionEmail(String userEmail, String connectionEmail) {

        ConnectionListElement element = getConnectionListElementByConnectionEmail(userEmail, connectionEmail);

        connectionListElementRepository.delete(element);
    }
}
