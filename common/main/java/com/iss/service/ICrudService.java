package com.iss.service;

public interface ICrudService<T,K> extends IService {
    void add(Object... objects);
    void remove(K key);
    void edit(T self);
    Iterable<T> getAll();
    Iterable<T> getAll(int count, int offset);
    int count();
}
