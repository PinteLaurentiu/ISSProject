package com.iss.service;

import com.iss.domain.Cerere;
import com.iss.persistance.CerereRepository;
import com.iss.persistance.RepositoryFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CerereService implements ICrudService<Cerere,Integer> {
    private final RepositoryFactory factory;
    private final ServiceFactory parent;

    public CerereService(RepositoryFactory factory, ServiceFactory parent) {
        this.factory = factory;
        this.parent = parent;
    }

    @Override
    public void add(Object... objects) {
        Cerere cerere = new Cerere();
        factory.get(CerereRepository.class).put(cerere);
    }

    @Override
    public void remove(Integer key) {
        factory.get(CerereRepository.class).remove(key);
    }

    @Override
    public void edit(Cerere self) {
        throw new NotImplementedException();
    }

    @Override
    public Iterable<Cerere> getAll() {
        return factory.get(CerereRepository.class).getAll();
    }

    @Override
    public Iterable<Cerere> getAll(int count, int offset) {
        return factory.get(CerereRepository.class).getAll(count, offset);
    }

    @Override
    public int count() {
        return factory.get(CerereRepository.class).count();
    }
}
