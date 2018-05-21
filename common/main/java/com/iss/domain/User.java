package com.iss.domain;

import java.util.Date;

public class User {

    private int id;
    private String nume;
    private transient String password;
    private String prenume;
    private Date birthday;
    private String localitate;
    private String judet;
    private String resedinta;
    private String localitate2;
    private String judet2;
    private String domiciliu;
    private String email;
    private String telefon;
    private Activation activation;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getResedinta() {
        return resedinta;
    }

    public void setResedinta(String resedinta) {
        this.resedinta = resedinta;
    }

    public String getLocalitate2() {
        return localitate2;
    }

    public void setLocalitate2(String localitate2) {
        this.localitate2 = localitate2;
    }

    public String getJudet2() {
        return judet2;
    }

    public void setJudet2(String judet2) {
        this.judet2 = judet2;
    }

    public String getDomiciliu() {
        return domiciliu;
    }

    public void setDomiciliu(String domiciliu) {
        this.domiciliu = domiciliu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Activation getActivation() {
        return activation;
    }

    public void setActivation(Activation activation) {
        this.activation = activation;
    }
}
