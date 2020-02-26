package com.openclassrooms.Project6Test.ModelsTest;

import com.openclassrooms.Project6Test.Models.User;
import com.openclassrooms.Project6Test.Models.UserModificationRegister;
import com.openclassrooms.Project6Test.Models.UserModificationType;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UserModificationRegisterTest {

    private int id = 1;

    private User user = new User();

    private UserModificationType userModificationType = new UserModificationType();

    private Date madeAt = new Date();

    private String previousDetails = "";

    private String newDetails = "";

    @Test
    public void parameterizedConstructor() {

        //Act
        UserModificationRegister newUserModificationRegister
                = new UserModificationRegister(user, userModificationType, madeAt);

        //Assert
        assertEquals(user, newUserModificationRegister.getUser());
        assertEquals(userModificationType, newUserModificationRegister.getUserModificationType());
        assertEquals(madeAt, newUserModificationRegister.getMadeAt());
    }

    @Test
    public void setId() {

        //Arrange
        UserModificationRegister newUserModificationRegister = new UserModificationRegister();

        //Act
        newUserModificationRegister.setId(id);

        //Assert
        assertEquals(id, newUserModificationRegister.getId());
    }

    @Test
    public void setUser() {

        //Arrange
        UserModificationRegister newUserModificationRegister = new UserModificationRegister();

        //Act
        newUserModificationRegister.setUser(user);

        //Assert
        assertEquals(user, newUserModificationRegister.getUser());
    }

    @Test
    public void setUserModificationType() {

        //Arrange
        UserModificationRegister newUserModificationRegister = new UserModificationRegister();

        //Act
        newUserModificationRegister.setUserModificationType(userModificationType);

        //Assert
        assertEquals(userModificationType, newUserModificationRegister.getUserModificationType());
    }

    @Test
    public void setMadeAt() {

        //Arrange
        UserModificationRegister newUserModificationRegister = new UserModificationRegister();

        //Act
        newUserModificationRegister.setMadeAt(madeAt);

        //Assert
        assertEquals(madeAt, newUserModificationRegister.getMadeAt());
    }

    @Test
    public void setPreviousDetails() {

        //Arrange
        UserModificationRegister newUserModificationRegister = new UserModificationRegister();

        //Act
        newUserModificationRegister.setPreviousDetails(previousDetails);

        //Assert
        assertEquals(previousDetails, newUserModificationRegister.getPreviousDetails());
    }

    @Test
    public void setNewDetails() {

        //Arrange
        UserModificationRegister newUserModificationRegister = new UserModificationRegister();

        //Act
        newUserModificationRegister.setNewDetails(newDetails);

        //Assert
        assertEquals(newDetails, newUserModificationRegister.getNewDetails());
    }
}
