package com.dorozco.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="sender")
    @NotNull
    private String sender;

    @Column(name="receiver")
    @NotNull
    private String receiver;

    @Column(name="status")
    @NotNull
    private String status;

    @Column(name="body")
    @NotNull
    private String body;

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Message(){}


    public Message(String sender, String receiver, String status, String body) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", status='" + status + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
