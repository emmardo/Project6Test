package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.UserModificationType;
import com.openclassrooms.Project6Test.Repositories.UserModificationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserModificationTypeService {

    @Autowired
    private UserModificationTypeRepository userModificationTypeRepository;


    public void createUserModificationTypes(List<String> userModificationTypeList) {

        userModificationTypeRepository.deleteAll();

        for (String userModificationType: userModificationTypeList) {

            UserModificationType roleInstance = new UserModificationType(userModificationType);

            userModificationTypeRepository.save(roleInstance);
        }
    }

    /*public void addUserModificationType(String userModificationType) {

        UserModificationType userModificationTypeInstance = new UserModificationType(userModificationType);

        userModificationTypeRepository.save(userModificationTypeInstance);
    }*/

    public List<String> getAllUserModificationTypes() {

        List<String> userModificationTypes = null;

        if(!userModificationTypeRepository.findAll().isEmpty()) {

            for (UserModificationType userModificationType : userModificationTypeRepository.findAll()) {

                userModificationTypes.add(userModificationType.getUserModificationType());
            }
        }

        return userModificationTypes;
    }

    /*public String getUserModificationTypeById(int userModificationTypeId) {

        return userModificationTypeRepository.findUserModificationTypeById(userModificationTypeId).getUserModificationType();
    }

    public void deleteUserModificationType(String userModificationType) {

        userModificationTypeRepository.delete(userModificationTypeRepository.findUserModificationTypeByUserModificationType(userModificationType));
    }*/
}
