package com.iss.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("ALL")
public class ProxyFactory extends BaseServiceFactory{

    private String host;
    private Long sessionId;

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
    @SuppressWarnings("unused")
    public ProxyFactory(String host) {
        this.host = host;
        this.sessionId = null;
    }

    @Override
    public <T,K> ObservableService getObservable(Class<? extends ICrudService<T,K>> tClass){
        if (crudServices.containsKey(tClass.getName())) {
            return getExistingObservableService(tClass);
        }
        try {
            Constructor<? extends ICrudService<T,K>> cons = tClass.getConstructor(String.class,ProxyFactory.class);
            crudServices.put(tClass.getName(), new ClientObservableService<>(cons.newInstance(host, this)));
            return getObservable(tClass);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ServerException("service not found!");
        }
    }

}
