package com.iss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iss.enums.GrupaSange;

public class Analiza {

    private int idAnaliza;
    private String imunoHematologice;
    private GrupaSange grupaSange;
    private String boliTransmisibile;
    @JsonIgnore
    private Donare donare;

    public int getIdAnaliza() {
        return idAnaliza;
    }

    public void setIdAnaliza(int idAnaliza) {
        this.idAnaliza = idAnaliza;
    }

    public String getImunoHematologice() {
        return imunoHematologice;
    }

    public void setImunoHematologice(String imunoHematologice) {
        this.imunoHematologice = imunoHematologice;
    }

    public GrupaSange getGrupaSange() {
        return grupaSange;
    }

    public void setGrupaSange(GrupaSange grupaSange) {
        this.grupaSange = grupaSange;
    }

    public String getBoliTransmisibile() {
        return boliTransmisibile;
    }

    public void setBoliTransmisibile(String boliTransmisibile) {
        this.boliTransmisibile = boliTransmisibile;
    }

    public Donare getDonare() {
        return donare;
    }

    public void setDonare(Donare donare) {
        this.donare = donare;
    }
}
