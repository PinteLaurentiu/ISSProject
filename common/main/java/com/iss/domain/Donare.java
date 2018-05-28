package com.iss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Donare {
    private int id;
    private Spital centru;
    private Date date;
    private String pentru;
    private DonareStatus status;
    private User user;
    private Consult consult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Spital getCentru() {
        return centru;
    }

    public void setCentru(Spital centru) {
        this.centru = centru;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPentru() {
        return pentru;
    }

    public void setPentru(String pentru) {
        this.pentru = pentru;
    }

    public DonareStatus getStatus() {
        return status;
    }

    public void setStatus(DonareStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
