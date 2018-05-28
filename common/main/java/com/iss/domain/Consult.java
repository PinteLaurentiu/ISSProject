package com.iss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Consult {
    private int idConsult;
    @JsonIgnore
    private Donare donare;
    private float greutate;
    private float tensiune;
    private int puls;
    private String boliDepistate;
    private float inaltime;
    private boolean apt;

    public Donare getDonare() {
        return donare;
    }

    public void setDonare(Donare donare) {
        this.donare = donare;
    }

    public int getIdConsult() {
        return idConsult;
    }

    public void setIdConsult(int idConsult) {
        this.idConsult = idConsult;
    }

    public float getGreutate() {
        return greutate;
    }

    public void setGreutate(float greutate) {
        this.greutate = greutate;
    }

    public float getTensiune() {
        return tensiune;
    }

    public void setTensiune(float tensiune) {
        this.tensiune = tensiune;
    }

    public int getPuls() {
        return puls;
    }

    public void setPuls(int puls) {
        this.puls = puls;
    }

    public String getBoliDepistate() {
        return boliDepistate;
    }

    public void setBoliDepistate(String boliDepistate) {
        this.boliDepistate = boliDepistate;
    }

    public float getInaltime() {
        return inaltime;
    }

    public void setInaltime(float inaltime) {
        this.inaltime = inaltime;
    }

    public boolean isApt() {
        return apt;
    }

    public void setApt(boolean apt) {
        this.apt = apt;
    }
}
