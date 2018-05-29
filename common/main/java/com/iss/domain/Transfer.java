package com.iss.domain;

public class Transfer {
    private int idTransfer;
    private String from;
    private String to;
    private PungaSange pungaSange;

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

    public PungaSange getPungaSange() {
        return pungaSange;
    }

    public void setPungaSange(PungaSange pungaSange) {
        this.pungaSange = pungaSange;
    }
}
