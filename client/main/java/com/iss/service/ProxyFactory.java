package com.iss.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("ALL")
public class ProxyFactory extends BaseServiceFactory{

    private String host;

    @SuppressWarnings("unused")
    public ProxyFactory(String host) {
        this.host = host;
    }

    @Override
    public <T,K> ObservableService getObservable(Class<? extends ICrudService<T,K>> tClass){
        if (crudServices.containsKey(tClass.getName())) {
            return getExistingObservableService(tClass);
        }
        try {
            Constructor<? extends ICrudService<T,K>> cons = tClass.getConstructor(String.class);
            crudServices.put(tClass.getName(), new ClientObservableService<>(cons.newInstance(host)));
            return getObservable(tClass);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ServerException("service not found!");
        }
    }

}
