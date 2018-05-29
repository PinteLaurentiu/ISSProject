package com.iss.domain;

import com.iss.enums.DonareStatus;

import java.util.Date;
import java.util.Set;

public class Donare {
    private int id;
    private Spital centru;
    private Date date;
    private String pentru;
    private DonareStatus status;
    private User user;
    private Consult consult;
    private Set<PungaSange> pungiSange;
    private Analiza analiza;

    public Consult getConsult() {
        return consult;
    }

    public void setConsult(Consult consult) {
        this.consult = consult;
    }

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



    @Override
    public String toString() {
        return "Donare{" +
                "id=" + id +
                ", centru=" + centru +
                ", date=" + date +
                ", pentru='" + pentru + '\'' +
                ", status=" + status +
                ", user=" + user +
                ", consult=" + consult +
                '}';
    }

    public Set<PungaSange> getPungiSange() {
        return pungiSange;
    }

    public void setPungiSange(Set<PungaSange> pungiSange) {
        this.pungiSange = pungiSange;
    }

    public Analiza getAnaliza() {
        return analiza;
    }

    public void setAnaliza(Analiza analiza) {
        this.analiza = analiza;
    }
}
