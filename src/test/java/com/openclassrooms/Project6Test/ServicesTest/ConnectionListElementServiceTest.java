package com.openclassrooms.Project6Test.ServicesTest;

import com.openclassrooms.Project6Test.Models.Account;
import com.openclassrooms.Project6Test.Models.Connection;
import com.openclassrooms.Project6Test.Models.ConnectionListElement;
import com.openclassrooms.Project6Test.Models.User;
import com.openclassrooms.Project6Test.Repositories.AccountRepository;
import com.openclassrooms.Project6Test.Repositories.ConnectionListElementRepository;
import com.openclassrooms.Project6Test.Repositories.ConnectionRepository;
import com.openclassrooms.Project6Test.Services.ConnectionListElementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionListElementServiceTest {

    @Mock
    private ConnectionListElementRepository connectionListElementRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ConnectionRepository connectionRepository;

    @InjectMocks
    private ConnectionListElementService connectionListElementService;

    @Test
    public void createConnectionListElement_connectionElementNonExistent_connectionListElementCreated() {

        //Arrange
        String userEmail = "abc@abc.com";
        User userEmailsUser = new User();
        userEmailsUser.setEmail(userEmail);
        Account userEmailsAccount = new Account();
        userEmailsAccount.setUser(userEmailsUser);

        String connectionEmail = "jkl@jkl.com";
        User connectionEmailsUser = new User();
        connectionEmailsUser.setEmail(connectionEmail);
        Connection connectionEmailsConnection = new Connection();
        connectionEmailsConnection.setUser(connectionEmailsUser);

        //Create 2 connectionListElements in userEmail's Account that are different from connectionEmail
        String email1 = "dce@dce.com";
        String email2 = "fgh@fgh.com";

        User email1User = new User();
        email1User.setEmail(email1);
        Connection email1Connection = new Connection();
        email1Connection.setUser(email1User);
        ConnectionListElement connectionListElement1 = new ConnectionListElement(userEmailsAccount, email1Connection);

        User email2User = new User();
        email2User.setEmail(email2);
        Connection email2Connection = new Connection();
        email2Connection.setUser(email2User);
        ConnectionListElement connectionListElement2 = new ConnectionListElement(userEmailsAccount, email2Connection);

        //Add new connectionListElements to List to use list as a Mock
        List<ConnectionListElement> connectionListElements = new ArrayList<>();
        connectionListElements.add(connectionListElement1);
        connectionListElements.add(connectionListElement2);

        //Create userEmail a new connectionListElement with connectionEmail's Connection
        ConnectionListElement newElement = new ConnectionListElement(userEmailsAccount, connectionEmailsConnection);

        when(connectionListElementRepository.findConnectionListElementsByAccountUserEmail(userEmail))
                .thenReturn(connectionListElements);

        when(accountRepository.findAccountByUserEmail(userEmail)).thenReturn(userEmailsAccount);

        when(connectionRepository.findConnectionByUserEmail(connectionEmail)).thenReturn(connectionEmailsConnection);

        //new connectionListElement added
        when(connectionListElementRepository.save(any(ConnectionListElement.class))).thenReturn(newElement);

        //Act
        connectionListElementService.createConnectionListElement(userEmail, connectionEmail);

        //Assert
        verify(connectionListElementRepository, times(1)).save(any(ConnectionListElement.class));
    }

    @Test
    public void createConnectionListElement_connectionElementExists_connectionListElementNotCreated() {

        //Arrange
        String userEmail = "abc@abc.com";
        User userEmailsUser = new User();
        userEmailsUser.setEmail(userEmail);
        Account userEmailsAccount = new Account();
        userEmailsAccount.setUser(userEmailsUser);

        String connectionEmail = "jkl@jkl.com";
        User connectionEmailsUser = new User();
        connectionEmailsUser.setEmail(connectionEmail);
        Connection connectionEmailsConnection = new Connection();
        connectionEmailsConnection.setUser(connectionEmailsUser);

        //Create 1 connectionListElement in userEmail's Account that is different from connectionEmail
        //and 1 connectionListElement that corresponds to connectionEmail
        String email1 = "dce@dce.com";

        User email1User = new User();
        email1User.setEmail(email1);
        Connection email1Connection = new Connection();
        email1Connection.setUser(email1User);
        ConnectionListElement connectionListElement1 = new ConnectionListElement(userEmailsAccount, email1Connection);

        ConnectionListElement connectionListElement2 = new ConnectionListElement(userEmailsAccount, connectionEmailsConnection);

        //Add new connectionListElements to List to use list as a Mock
        List<ConnectionListElement> connectionListElements = new ArrayList<>();
        connectionListElements.add(connectionListElement1);
        connectionListElements.add(connectionListElement2);

        when(connectionListElementRepository.findConnectionListElementsByAccountUserEmail(userEmail))
                .thenReturn(connectionListElements);

        //Act
        connectionListElementService.createConnectionListElement(userEmail, connectionEmail);

        //Assert
        verify(connectionListElementRepository, times(0)).save(any(ConnectionListElement.class));
    }

    @Test
    public void getConnectionListElementsByUserEmail_connectionListElementExist_connectionListElementsReturned() {

        //Arrange
        String email1 = "abc@abc.com";
        ConnectionListElement connectionListElement1 = new ConnectionListElement();
        ConnectionListElement connectionListElement2 = new ConnectionListElement();
        List<ConnectionListElement> connectionListElements = new ArrayList<>();

        connectionListElements.add(connectionListElement1);
        connectionListElements.add(connectionListElement2);

        when(connectionListElementRepository.findConnectionListElementsByAccountUserEmail(anyString()))
                .thenReturn(connectionListElements);

        //Act
        List<ConnectionListElement> list = connectionListElementService.getConnectionListElementsByUserEmail(email1);

        //Assert
        assertEquals(2, list.size());
    }

    @Test
    public void getConnectionListElementsByUserEmail_connectionListElementNonExistent_emptyListReturned() {

        //Arrange
        String email1 = "abc@abc.com";
        List<ConnectionListElement> connectionListElements = new ArrayList<>();

        when(connectionListElementRepository.findConnectionListElementsByAccountUserEmail(anyString()))
                .thenReturn(connectionListElements);

        //Act
        List<ConnectionListElement> list = connectionListElementService.getConnectionListElementsByUserEmail(email1);

        //Assert
        assertEquals(0, list.size());
    }

    @Test
    public void getAUsersConnectionsEmailsByUserEmail_connectionsListNull_emptyListReturned() {

        //Arrange
        String email = "abc@abc.com";

        List<ConnectionListElement> connectionListElements = null;

        when(connectionListElementService.getConnectionListElementsByUserEmail(email))
                .thenReturn(connectionListElements);

        //Act
        List<String> list = connectionListElementService.getAUsersConnectionsEmailsByUserEmail(email);

        //Assert
        assertEquals(true, list.isEmpty());

    }

    @Test
    public void getAUsersConnectionsEmailsByUserEmail_connectionsListNotNull_connectionsListReturned() {

        //Arrange
        String email1 = "abc@abc.com";
        String email2 = "dce@dce.com";
        String email3 = "fgh@fgh.com";
        User user1 = new User();
        User user2 = new User();
        Connection connection1 = new Connection();
        Connection connection2 = new Connection();
        ConnectionListElement connectionListElement1 = new ConnectionListElement();
        ConnectionListElement connectionListElement2 = new ConnectionListElement();
        List<ConnectionListElement> connectionListElements = new ArrayList<>();

        //created a connectionListElement different than the one that will be looked for
        user1.setEmail(email1);
        user2.setEmail(email2);
        connection1.setUser(user1);
        connection2.setUser(user2);
        connectionListElement1.setConnection(connection1);
        connectionListElement2.setConnection(connection2);
        connectionListElements.add(connectionListElement1);
        connectionListElements.add(connectionListElement2);

        when(connectionListElementService.getConnectionListElementsByUserEmail(email3))
                .thenReturn(connectionListElements);

        //Act
        List<String> list = connectionListElementService.getAUsersConnectionsEmailsByUserEmail(email3);

        //Assert
        assertEquals(2, list.size());
        assertEquals(email2, list.get(1));
    }
}
