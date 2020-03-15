package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.User;
import com.openclassrooms.Project6Test.Models.UserModificationRegister;
import com.openclassrooms.Project6Test.Models.UserModificationType;
import com.openclassrooms.Project6Test.Repositories.UserModificationRegisterRepository;
import com.openclassrooms.Project6Test.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserModificationRegisterService {


    private UserRepository userRepository;

    private UserModificationRegisterRepository userModificationRegisterRepository;


    @Autowired
    public UserModificationRegisterService(UserRepository userRepository, UserModificationRegisterRepository userModificationRegisterRepository) {

        this.userRepository = userRepository;
        this.userModificationRegisterRepository = userModificationRegisterRepository;
    }


    public void createUserModificationRegister(String email, String userModificationTypeString, String previousDetails, String newDetails) {

        User user = userRepository.findUserByEmail(email);

        UserModificationType userModificationType = new UserModificationType(userModificationTypeString);

        Date date = new Date();

        UserModificationRegister userModificationRegister = new UserModificationRegister(user, userModificationType, date);

        if(userModificationTypeString.equals("Email")) {

            userModificationRegister.setPreviousDetails(previousDetails);

            userModificationRegister.setNewDetails(newDetails);
        }

        userModificationRegisterRepository.save(userModificationRegister);
    }


    /*public UserModificationRegister getUserModificationRegisterById(int userModificationRegisterId) {

        return userModificationRegisterRepository.findUserModificationRegisterById(userModificationRegisterId);
    }


    public List<UserModificationRegister> getAllUserModificationRegistersByUserEmail(String userEmail) {

        return userModificationRegisterRepository.findUserModificationRegisterByUserEmail(userEmail);
    }


    public List<UserModificationRegister> getAllUserModificationRegisters() {

        return userModificationRegisterRepository.findAll();
    }


    public void deleteUserModificationRegisterById(int userModificationRegisterId) {

        userModificationRegisterRepository.deleteById(userModificationRegisterId);
    }


    public void deleteUserModificationRegistersByUserEmail(String userEmail) {

        userModificationRegisterRepository
                .deleteAll(userModificationRegisterRepository.findUserModificationRegisterByUserEmail(userEmail));
    }


    public void deleteAllUserModificationRegisters() {

        userModificationRegisterRepository.deleteAll();
    }*/
}
