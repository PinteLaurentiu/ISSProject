package com.iss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iss.enums.TipComponenteSange;

import java.util.Date;
import java.util.Set;

public class ComponentaSange {
    private int idComponenta;
    @JsonIgnore
    private Donare donare;
    private TipComponenteSange tipComponentaSange;
    private Date dataExpirare;
    private String locatie;
    @JsonIgnore
    private Set<CompabilitateMajora> probe;
    private Set<TransferComponenta> transferuri;

    public Set<TransferComponenta> getTransferuri() {
        return transferuri;
    }

    public void setTransferuri(Set<TransferComponenta> transferuri) {
        this.transferuri = transferuri;
    }

    public Set<CompabilitateMajora> getProbe() {
        return probe;
    }

    public void setProbe(Set<CompabilitateMajora> probe) {
        this.probe = probe;
    }

    public int getIdComponenta() {
        return idComponenta;
    }

    public void setIdComponenta(int idComponenta) {
        this.idComponenta = idComponenta;
    }

    public Donare getDonare() {
        return donare;
    }

    public void setDonare(Donare donare) {
        this.donare = donare;
    }

    public TipComponenteSange getTipComponentaSange() {
        return tipComponentaSange;
    }

    public void setTipComponentaSange(TipComponenteSange tipComponentaSange) {
        this.tipComponentaSange = tipComponentaSange;
    }

    public Date getDataExpirare() {
        return dataExpirare;
    }

    public void setDataExpirare(Date dataExpirare) {
        this.dataExpirare = dataExpirare;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }
}
