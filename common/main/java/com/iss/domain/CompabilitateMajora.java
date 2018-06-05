package com.iss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class CompabilitateMajora implements Serializable{

    @JsonIgnore
    private Cerere cerere;
    private ComponentaSange componentaSange;
    private boolean acceptat;

    public Cerere getCerere() {
        return cerere;
    }

    public void setCerere(Cerere cerere) {
        this.cerere = cerere;
    }

    public ComponentaSange getComponentaSange() {
        return componentaSange;
    }

    public void setComponentaSange(ComponentaSange componentaSange) {
        this.componentaSange = componentaSange;
    }

    public boolean isAcceptat() {
        return acceptat;
    }

    public void setAcceptat(boolean acceptat) {
        this.acceptat = acceptat;
    }
}
