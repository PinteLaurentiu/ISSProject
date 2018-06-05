package com.iss.domain;

import com.iss.enums.CerereStatus;
import com.iss.enums.TipComponenteSange;
import com.iss.enums.GradDeUrgenta;
import com.iss.enums.GrupaSange;

import java.util.Set;

public class Cerere {
    private int idCerere;
    private String numePacient;
    private String prenumePacient;
    private TipComponenteSange tipComponenteSange;
    private GradDeUrgenta gradDeUrgenta;
    private CerereStatus cerereStatus;
    private GrupaSange grupaSange;
    private int cantitatea;
    private String locatie;
    private Set<CompabilitateMajora> probe;
    private Integer cantitateDonata;

    public Integer getCantitateDonata() {
        return cantitateDonata;
    }

    public void setCantitateDonata(Integer cantitateDonata) {
        this.cantitateDonata = cantitateDonata;
    }

    public Set<CompabilitateMajora> getProbe() {
        return probe;
    }

    public void setProbe(Set<CompabilitateMajora> probe) {
        this.probe = probe;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public Cerere() {
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

    public TipComponenteSange getTipComponenteSange() {
        return tipComponenteSange;
    }

    public void setTipComponenteSange(TipComponenteSange tipComponenteSange) {
        this.tipComponenteSange = tipComponenteSange;
    }

    public int getCantitatea() {
        return cantitatea;
    }

    public void setCantitatea(int cantitatea) {
        this.cantitatea = cantitatea;
    }

    public GradDeUrgenta getGradDeUrgenta() {
        return gradDeUrgenta;
    }

    public void setGradDeUrgenta(GradDeUrgenta gradDeUrgenta) {
        this.gradDeUrgenta = gradDeUrgenta;
    }

    public GrupaSange getGrupaSange() {
        return grupaSange;
    }

    public void setGrupaSange(GrupaSange grupaSange) {
        this.grupaSange = grupaSange;
    }

    public CerereStatus getCerereStatus() {
        return cerereStatus;
    }

    public void setCerereStatus(CerereStatus cerereStatus) {
        this.cerereStatus = cerereStatus;
    }
}
