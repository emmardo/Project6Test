package com.openclassrooms.Project6Test.Models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "connection", catalog = "pay_my_buddy")
@EntityListeners(AuditingEntityListener.class)
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "connection_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_connection_type_id")
    /*@Column(name = "fk_connection_type_id")*/
    private ConnectionType connectionType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id")
    /*@Column(name = "fk_user_id")*/
    private User user;

    @OneToMany(mappedBy = "connection")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "connection")
    private List<ConnectionListElement> connectionListElements;

    public Connection() {
    }

    public Connection(ConnectionType connectionType, User user) {

        this.connectionType = connectionType;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<ConnectionListElement> getConnectionListElements() {
        return connectionListElements;
    }

    public void setConnectionListElements(List<ConnectionListElement> connectionListElements) {
        this.connectionListElements = connectionListElements;
    }
}
