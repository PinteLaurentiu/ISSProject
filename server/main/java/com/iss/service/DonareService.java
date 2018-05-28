package com.iss.service;

import com.iss.domain.Donare;
import com.iss.domain.DonareStatus;
import com.iss.domain.Spital;
import com.iss.domain.User;
import com.iss.persistance.DonareRepository;
import com.iss.persistance.RepositoryFactory;
import com.iss.persistance.UserRepository;

import java.util.Date;

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
}
