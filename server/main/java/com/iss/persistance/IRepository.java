package com.iss.persistance;

public interface IRepository<T, K> extends Iterable<T>{
    void put(T elem);
    void remove(K key);
    Iterable<T> getAll();
}
