package com.iss.service;

import com.iss.domain.User;
import com.iss.persistance.RepositoryFactory;
import com.iss.persistance.UserRepository;

public class UserService implements ICrudService<User,Integer> {

    private final RepositoryFactory factory;

    public UserService(RepositoryFactory factory) {
        this.factory = factory;
    }

    @Override
    public void add(Object... objects) {

    }

    @Override
    public void remove(Integer key) {

    }

    @Override
    public void edit(User self) {

    }

    @Override
    public Iterable<User> getAll() {
        return null;
    }

    public boolean login(String user, String pass){
        return factory.get(UserRepository.class).login(user, pass);
    }
}
