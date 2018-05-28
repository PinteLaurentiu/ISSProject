package com.iss.service;

import com.iss.domain.Consult;
import com.iss.domain.Donare;
import com.iss.domain.DonareStatus;
import com.iss.persistance.ConsultRepository;
import com.iss.persistance.DonareRepository;
import com.iss.persistance.RepositoryFactory;

public class ConsultService implements ICrudService<Consult, Integer> {

    private final RepositoryFactory factory;
    private final ServiceFactory parent;

    public ConsultService(RepositoryFactory factory, ServiceFactory parent) {
        this.factory = factory;
        this.parent = parent;
    }

    @Override
    public void add(Object... objects) {
        Integer idConsult = (Integer) objects[0];
        Float greutate = (Float) objects[1];
        Float tensiune = (Float) objects[2];
        Integer puls = (Integer) objects[3];
        String boliDepistate  = (String) objects[4];
        Float inaltime = (Float) objects[5];
        Boolean apt = (Boolean) objects[6];

        Consult consult = new Consult();
        consult.setGreutate(greutate);
        consult.setTensiune(tensiune);
        consult.setPuls(puls);
        consult.setBoliDepistate(boliDepistate);
        consult.setInaltime(inaltime);
        consult.setApt(apt);

        Donare donare = factory.get(DonareRepository.class).getById(idConsult);
        consult.setDonare(donare);
        donare.setConsult(consult);
        factory.get(ConsultRepository.class).put(consult);

        if (!apt) {
            parent.get(DonareService.class).setStatus(donare.getId(), DonareStatus.RESPINS);
        } else {
            parent.get(DonareService.class).setStatus(donare.getId(), DonareStatus.CONSULTAT);
        }
    }

    @Override
    public void remove(Integer key) {
        factory.get(ConsultRepository.class).remove(key);
    }

    @Override
    public void edit(Consult self) {
        factory.get(ConsultRepository.class).put(self);
    }

    @Override
    public Iterable<Consult> getAll() {
        return factory.get(ConsultRepository.class).getAll();
    }

    @Override
    public Iterable<Consult> getAll(int count, int offset) {
        return factory.get(ConsultRepository.class).getAll(count, offset);

    }

    @Override
    public int count() {
        return factory.get(ConsultRepository.class).count();
    }
}
