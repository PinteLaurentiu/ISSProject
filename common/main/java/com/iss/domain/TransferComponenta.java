package com.iss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TransferComponenta {

    private int idTransfer;
    private String from;
    private String to;
    @JsonIgnore
    private ComponentaSange componentaSange;

    public int getIdTransfer() {
        return idTransfer;
    }

    public void setIdTransfer(int idTransfer) {
        this.idTransfer = idTransfer;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public ComponentaSange getComponentaSange() {
        return componentaSange;
    }

    public void setComponentaSange(ComponentaSange componentaSange) {
        this.componentaSange = componentaSange;
    }
}
