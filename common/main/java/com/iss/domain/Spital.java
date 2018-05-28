package com.iss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

public class Spital {
    private int id;
    private String nume;
    @JsonIgnore
    private Set<Donare> donari;

    public Set<Donare> getDonari() {
        return donari;
    }

    public void setDonari(Set<Donare> donari) {
        this.donari = donari;
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

    @Override
    public String toString() {
        return nume;
    }
}
