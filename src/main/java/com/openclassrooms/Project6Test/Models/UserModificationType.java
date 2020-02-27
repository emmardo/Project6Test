package com.openclassrooms.Project6Test.Models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_modification_type", catalog = "pay_my_buddy")
public class UserModificationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_modification_type_id")
    private int id;

    /*2 Types: Email(1) and Password(2)*/
    @Column(name = "user_modification_type")
    private String  userModificationType;

    @OneToMany(mappedBy = "userModificationType")
    private List<UserModificationRegister> userModificationRegisters;

    public UserModificationType() {
    }

    public UserModificationType(String userModificationType) {

        this.userModificationType = userModificationType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserModificationType() {
        return userModificationType;
    }

    public void setUserModificationType(String userModificationType) {
        this.userModificationType = userModificationType;
    }

    public List<UserModificationRegister> getUserModificationRegisters() {
        return userModificationRegisters;
    }

    public void setUserModificationRegisters(List<UserModificationRegister> userModificationRegisters) {
        this.userModificationRegisters = userModificationRegisters;
    }
}
