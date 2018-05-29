package com.iss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iss.enums.TipPungaSange;

import java.util.Set;

public class PungaSange {
    private int idSange;
    private TipPungaSange tip;
    private String locatie;
    @JsonIgnore
    private Donare donare;
    private Set<Transfer>  transferuri;

    public Set<Transfer> getTransferuri() {
        return transferuri;
    }

    public void setTransferuri(Set<Transfer> transferuri) {
        this.transferuri = transferuri;
    }

    public int getIdSange() {
        return idSange;
    }

    public void setIdSange(int idSange) {
        this.idSange = idSange;
    }

    public TipPungaSange getTip() {
        return tip;
    }

    public void setTip(TipPungaSange tip) {
        this.tip = tip;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public Donare getDonare() {
        return donare;
    }

    public void setDonare(Donare donare) {
        this.donare = donare;
    }
}
