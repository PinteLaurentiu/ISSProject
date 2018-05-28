package com.iss.service;

import com.iss.domain.Transfer;
import com.iss.domain.User;
import com.iss.persistance.RepositoryFactory;
import com.iss.persistance.TransferRepository;
import com.iss.persistance.UserRepository;

public class TransferService implements ICrudService<Transfer,Integer>{
    private final RepositoryFactory factory;

    public TransferService(RepositoryFactory factory) {
        this.factory = factory;
    }

    @Override
    public void add(Object... objects) {
        User user = new User();

        factory.get(UserRepository.class).put(user);
    }

    @Override
    public void remove(Integer key) {
        factory.get(TransferRepository.class).remove(key);
    }

    @Override
    public void edit(Transfer self) {
    }

    @Override
    public Iterable<Transfer> getAll() {
        return factory.get(TransferRepository.class).getAll();
    }

    @Override
    public Iterable<Transfer> getAll(int count, int offset) {
        return factory.get(TransferRepository.class).getAll(count, offset);
    }

    @Override
    public int count() {
        return factory.get(TransferRepository.class).count();
    }
}
