package com.iss.service;

import com.iss.domain.*;
import com.iss.enums.DonareStatus;
import com.iss.enums.GrupaSange;
import com.iss.enums.TipComponenteSange;
import com.iss.enums.TipPungaSange;
import com.iss.persistance.DonareRepository;
import com.iss.persistance.PungaSangeRepository;
import com.iss.persistance.RepositoryFactory;
import com.iss.persistance.UserRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class DonareService implements ICrudService<Donare,Integer> {
    private final RepositoryFactory factory;
    private final ServiceFactory parent;

    public DonareService(RepositoryFactory factory, ServiceFactory parent) {
        this.factory = factory;
        this.parent = parent;
    }


    @Override
    public void add(Object... objects) {
        Donare donare = new Donare();
        donare.setCentru((Spital) objects[0]);
        donare.setDate((Date)objects[1]);
        donare.setPentru((String) objects[2]);
        donare.setStatus((DonareStatus) objects[3]);
        User user  = factory.get(UserRepository.class).getByEmail((String) objects[4]);
        donare.setUser(user);
        user.getDonari().add(donare);
        donare.setConsult(null);
        factory.get(DonareRepository.class).put(donare);
    }

    @Override
    public void remove(Integer key) {
        factory.get(DonareRepository.class).remove(key);
    }

    @Override
    public void edit(Donare self) {
        factory.get(DonareRepository.class).put(self);
    }

    @Override
    public Iterable<Donare> getAll() {
        return factory.get(DonareRepository.class).getAll();
    }

    @Override
    public Iterable<Donare> getAll(int count, int offset) {
        return factory.get(DonareRepository.class).getAll(count, offset);
    }

    @Override
    public int count() {
        return factory.get(DonareRepository.class).count();
    }


    public Iterable<Donare> getByUser(String email){
        User user = factory.get(UserRepository.class).getByEmail(email);
        return user.getDonari();
    }

    public void setStatus(int id, DonareStatus status) {
        Donare donare = factory.get(DonareRepository.class).getById(id);
        donare.setStatus(status);
        factory.get(DonareRepository.class).put(donare);
    }

    public boolean exists(Integer id){
        return factory.get(DonareRepository.class).getById(id) != null;
    }

    public void finalizare(Integer id) {
        Donare donare = factory.get(DonareRepository.class).getById(id);
        donare.setPungiSange(new HashSet<>());

        PungaSange sangeAnalize = new PungaSange();
        sangeAnalize.setLocatie(donare.getCentru().getNume());
        sangeAnalize.setTip(TipPungaSange.Proba);
        sangeAnalize.setDonare(donare);

        donare.getPungiSange().add(sangeAnalize);
        factory.get(PungaSangeRepository.class).put(sangeAnalize);

        PungaSange sangeUtilizabil = new PungaSange();
        sangeUtilizabil.setLocatie(donare.getCentru().getNume());
        sangeUtilizabil.setTip(TipPungaSange.Utilizabil);
        sangeUtilizabil.setDonare(donare);

        donare.getPungiSange().add(sangeUtilizabil);
        factory.get(PungaSangeRepository.class).put(sangeUtilizabil);
    }

    public void addTransfer(int idPungaSange, String to) {
        PungaSange pungaSange = factory.get(PungaSangeRepository.class).getById(idPungaSange);
        Transfer transfer = new Transfer();
        transfer.setFrom(pungaSange.getLocatie());
        transfer.setTo(to);
        transfer.setPungaSange(pungaSange);
        pungaSange.setLocatie(to);
        factory.get(PungaSangeRepository.class).put(pungaSange);
    }

    public boolean existsPunga(Integer idPunga) {
        return factory.get(PungaSangeRepository.class).getById(idPunga) != null;
    }

    public void addAnaliza(Integer idDonare, String boli, String imunoH, GrupaSange grupaSange) {
        Analiza analiza =  new Analiza();
        analiza.setBoliTransmisibile(boli);
        analiza.setImunoHematologice(imunoH);
        analiza.setGrupaSange(grupaSange);

        Donare donare = factory.get(DonareRepository.class).getById(idDonare);
        analiza.setDonare(donare);
        donare.setAnaliza(analiza);

        factory.get(DonareRepository.class).put(donare);
    }

    public void addComponente(Integer idDonare) {
        Donare donare = factory.get(DonareRepository.class).getById(idDonare);
        String locatie = "";
        for (PungaSange pungaSange : donare.getPungiSange())
            if (pungaSange.getTip() == TipPungaSange.Utilizabil)
                locatie = pungaSange.getLocatie();

        donare.setComponenteSange(new HashSet<>());

        for (TipComponenteSange tip : TipComponenteSange.values()){
            ComponentaSange componentaSange = new ComponentaSange();
            componentaSange.setDonare(donare);
            componentaSange.setLocatie(locatie);
            componentaSange.setTipComponentaSange(tip);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(donare.getDate());

            if (tip == TipComponenteSange.Trombocite)
                calendar.add(Calendar.DAY_OF_YEAR, 5);
            else if (tip == TipComponenteSange.CeluleRosii)
                calendar.add(Calendar.DAY_OF_YEAR, 42);
            else
                calendar.add(Calendar.YEAR, 1);

            componentaSange.setDataExpirare(calendar.getTime());
            donare.getComponenteSange().add(componentaSange);
        }

        factory.get(DonareRepository.class).put(donare);
    }
}
