package com.iss.domain;

public class Cerere {
    private int idCerere;
    private String numePacient;
    private String prenumePacient;
    private int varsta;
    private ComponenteSange componenteSange;
    private int cantitatea;

    public Cerere() {
    }

    public Cerere(String numePacient, String prenumePacient, int varsta, ComponenteSange componenteSange, int cantitatea) {
        this.numePacient = numePacient;
        this.prenumePacient = prenumePacient;
        this.varsta = varsta;
        this.componenteSange = componenteSange;
        this.cantitatea = cantitatea;
    }

    public int getIdCerere() {
        return idCerere;
    }

    public void setIdCerere(int idCerere) {
        this.idCerere = idCerere;
    }

    public String getNumePacient() {
        return numePacient;
    }

    public void setNumePacient(String numePacient) {
        this.numePacient = numePacient;
    }

    public String getPrenumePacient() {
        return prenumePacient;
    }

    public void setPrenumePacient(String prenumePacient) {
        this.prenumePacient = prenumePacient;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public ComponenteSange getComponenteSange() {
        return componenteSange;
    }

    public void setComponenteSange(ComponenteSange componenteSange) {
        this.componenteSange = componenteSange;
    }

    public int getCantitatea() {
        return cantitatea;
    }

    public void setCantitatea(int cantitatea) {
        this.cantitatea = cantitatea;
    }
}
