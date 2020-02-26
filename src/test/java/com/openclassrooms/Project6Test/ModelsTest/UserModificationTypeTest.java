package com.openclassrooms.Project6Test.ModelsTest;

import com.openclassrooms.Project6Test.Models.UserModificationRegister;
import com.openclassrooms.Project6Test.Models.UserModificationType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserModificationTypeTest {

    private int id = 1;

    private String  userModificationType = "";

    private List<UserModificationRegister> userModificationRegisters = new ArrayList<>();

    private UserModificationRegister userModificationRegister = new UserModificationRegister();

    @Test
    public void parameterizedConstructor() {

        //Act
        UserModificationType newUserModificationType = new UserModificationType(userModificationType);

        //Assert
        assertEquals(userModificationType, newUserModificationType.getUserModificationType());
    }

    @Test
    public void setId() {

        //Arrange
        UserModificationType newUserModificationType = new UserModificationType();

        //Act
        newUserModificationType.setId(id);

        //Assert
        assertEquals(id, newUserModificationType.getId());
    }

    @Test
    public void setUserModificationType() {

        //Arrange
        UserModificationType newUserModificationType = new UserModificationType();

        //Act
        newUserModificationType.setUserModificationType(userModificationType);

        //Assert
        assertEquals(userModificationType, newUserModificationType.getUserModificationType());
    }

    @Test
    public void setUserModificationRegisters() {

        //Arrange
        UserModificationType newUserModificationType = new UserModificationType();

        userModificationRegisters.add(userModificationRegister);

        //Act
        newUserModificationType.setUserModificationRegisters(userModificationRegisters);

        //Assert
        assertEquals(userModificationRegisters, newUserModificationType.getUserModificationRegisters());
    }
}
