package com.iss.service;

public class ServerObservableService<T, K> extends ObservableService<T, K> {
    public ServerObservableService(ICrudService<T, K> target) {
        super(target);
    }

    @Override
    public void add(Object... objects) {
        target.add(objects);
        triggerEvent();
    }

    @Override
    public void remove(K key) {
        target.remove(key);
        triggerEvent();
    }

    @Override
    public void edit(T self) {
        target.edit(self);
        triggerEvent();
    }
}
