package com.iss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

@Component
public abstract class BaseServiceFactory {

    HashMap<String, IService> services;
    HashMap<String, ObservableService> crudServices;

    @Autowired
    public BaseServiceFactory() {
        this.services = new HashMap<>();
        this.crudServices = new HashMap<>();
    }

    public <T extends ICrudService<T2,K>,T2,K> T get(Class<T> tClass) {
        //noinspection unchecked
        return (T) getObservable(tClass).getTarget();
    }

    @SuppressWarnings({"WeakerAccess", "unused"})
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
            throw new ServerException("service not found!");
        }

    }

    public abstract  <T,K> ObservableService getObservable(Class<? extends ICrudService<T,K>> tClass);

    <T, K> ObservableService getExistingObservableService(Class<? extends ICrudService<T, K>> tClass) {
        ObservableService service = crudServices.get(tClass.getName());
        if (!tClass.isInstance(service.getTarget()))
            return null;
        return service;
    }
}
