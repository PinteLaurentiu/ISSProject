package com.iss.service;

public class ClientObservableService<T, K> extends ObservableService<T, K> {
    public ClientObservableService(ICrudService<T, K> iCrudService) {
        super(iCrudService);
    }

    @Override
    public void add(Object... objects) {
        target.add(objects);
    }

    @Override
    public void remove(K key) {
        target.remove(key);
    }

    @Override
    public void edit(T self) {
        target.edit(self);
    }
}
