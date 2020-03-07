package com.openclassrooms.Project6Test.Models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_modification_register", catalog = "pay_my_buddy")
@EntityListeners(AuditingEntityListener.class)
public class UserModificationRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_modification_register_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id")
    /*@Column(name = "fk_user_id")*/
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_modification_type_id")
    /*@Column(name = "fk_user_modification_type_id")*/
    private UserModificationType userModificationType;

    @Column(name = "made_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date madeAt;

    @Column(name = "previous_details")
    private String previousDetails;

    @Column(name = "new_details")
    private String newDetails;

    public UserModificationRegister() {
    }

    public UserModificationRegister(User user, UserModificationType userModificationType, Date madeAt) {

        this.user = user;
        this.userModificationType = userModificationType;
        this.madeAt = madeAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserModificationType getUserModificationType() {
        return userModificationType;
    }

    public void setUserModificationType(UserModificationType userModificationType) {
        this.userModificationType = userModificationType;
    }

    public Date getMadeAt() {
        return madeAt;
    }

    public void setMadeAt(Date madeAt) {
        this.madeAt = madeAt;
    }

    public String getPreviousDetails() {
        return previousDetails;
    }

    public void setPreviousDetails(String previousDetails) {
        this.previousDetails = previousDetails;
    }

    public String getNewDetails() {
        return newDetails;
    }

    public void setNewDetails(String newDetails) {
        this.newDetails = newDetails;
    }
}
