package com.iss.service;

import com.iss.database_connection.DatabaseException;
import com.iss.persistance.RepositoryFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ServiceFactory {

    private HashMap<String, IService> services;
    private HashMap<String, ObservableService> crudServices;
    private RepositoryFactory repositoryFactory;

    public ServiceFactory(RepositoryFactory repositoryFactory) {
        this.services = new HashMap<>();
        this.crudServices = new HashMap<>();
        this.repositoryFactory = repositoryFactory;
    }

    public <T extends ICrudService<T2,K>,T2,K> T get(Class<? extends ICrudService<T2,K>> tClass) {
        //noinspection unchecked
        return (T) getObservable(tClass).getTarget();
    }

    public <T extends IService> T getService(Class<T> tClass){
        if (services.containsKey(tClass.getName())) {
            IService service = services.get(tClass.getName());
            if (!tClass.isInstance(service))
                return null;
            return tClass.cast(service);
        }
        try {
            Constructor<T> cons = tClass.getConstructor();
            services.put(tClass.getName(), cons.newInstance());
            return getService(tClass);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new DatabaseException("service not found!");
        }

    }

    public <T,K> ObservableService getObservable(Class<? extends ICrudService<T,K>> tClass){
        if (services.containsKey(tClass.getName())) {
            ObservableService service = crudServices.get(tClass.getName());
            if (!tClass.isInstance(service))
                return null;
            return service;
        }
        try {
            Constructor<? extends ICrudService<T,K>> cons = tClass.getConstructor(RepositoryFactory.class);
            crudServices.put(tClass.getName(), new ServerObservableService<>(cons.newInstance(repositoryFactory)));
            return getObservable(tClass);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new DatabaseException("service not found!");
        }
    }
}
