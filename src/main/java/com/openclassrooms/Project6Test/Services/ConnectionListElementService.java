package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.Account;
import com.openclassrooms.Project6Test.Models.Connection;
import com.openclassrooms.Project6Test.Models.ConnectionListElement;
import com.openclassrooms.Project6Test.Repositories.AccountRepository;
import com.openclassrooms.Project6Test.Repositories.ConnectionListElementRepository;
import com.openclassrooms.Project6Test.Repositories.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        if(connectionListElementRepository.findConnectionListElementsByAccountUserEmail(userEmail).stream().noneMatch(
                element -> element.getConnection().getUser().getUser().equals(connectionEmail))) {

            Account account = accountRepository.findAccountByUserEmail(userEmail);

            Connection connection = connectionRepository.findConnectionByUserEmail(connectionEmail);

            ConnectionListElement connectionListElement = new ConnectionListElement(account, connection);

            connectionListElementRepository.save(connectionListElement);
        }
    }

    public List<ConnectionListElement> getConnectionListElementsByUserEmail(String userEmail) {

        return connectionListElementRepository.findConnectionListElementsByAccountUserEmail(userEmail);
    }

    public List<String> getAUsersConnectionsEmailsByUserEmail(String userEmail) {

        List<String> connectionsEmails = new ArrayList<>();

        if(getConnectionListElementsByUserEmail(userEmail) != null) {

            for (ConnectionListElement connectionListElement : getConnectionListElementsByUserEmail(userEmail)) {

                connectionsEmails.add(connectionListElement.getConnection().getUser().getUser());
            }
        }

        return connectionsEmails;
    }
}
