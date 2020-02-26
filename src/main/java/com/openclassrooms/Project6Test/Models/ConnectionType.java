package com.openclassrooms.Project6Test.Models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "connection_type", catalog = "pay_my_buddy")
@EntityListeners(AuditingEntityListener.class)
public class ConnectionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "connection_type_id")
    private int id;

    /*2 Types: Regular(1) and Company(2)*/
    @Column(name = "connection_type")
    private String connectionType;

    @OneToMany(mappedBy = "connectionType")
    private List<Connection> connections;

    public ConnectionType() {
    }

    public ConnectionType(String connectionType) {

        this.connectionType = connectionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}
