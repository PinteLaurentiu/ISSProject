package com.iss.service;

import com.iss.domain.Spital;
import com.iss.persistance.RepositoryFactory;
import com.iss.persistance.SpitalRepository;

import java.util.List;

public class SpitalService implements ICrudService<Spital, Integer> {

    private final RepositoryFactory factory;
    private final ServiceFactory parent;

    public SpitalService(RepositoryFactory factory, ServiceFactory parent) {
        this.factory = factory;
        this.parent = parent;
    }


    @Override
    public void add(Object... objects) {
    }

    @Override
    public void remove(Integer key) {
    }

    @Override
    public void edit(Spital self) {
    }

    @Override
    public Iterable<Spital> getAll() {
        return factory.get(SpitalRepository.class).getAll();
    }

    @Override
    public Iterable<Spital> getAll(int count, int offset) {
        return factory.get(SpitalRepository.class).getAll(count, offset);
    }

    @Override
    public int count() {
        return factory.get(SpitalRepository.class).count();
    }

    public Spital getByNume(String nume) {
        List<Spital> spitalList = (List<Spital>) getAll();
        for (Spital spital : spitalList) {
            if (spital.getNume().equals(nume)) {
                return spital;
            }
        }
        return null;
    }
}
