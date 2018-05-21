package com.iss.service;

import com.iss.database_connection.DatabaseException;
import com.iss.persistance.RepositoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

@Component
public class ServiceFactory extends BaseServiceFactory{

    private RepositoryFactory repositoryFactory;

    @Autowired
    public ServiceFactory(RepositoryFactory repositoryFactory) {
        super();
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public <T,K> ObservableService getObservable(Class<? extends ICrudService<T,K>> tClass){
        if (crudServices.containsKey(tClass.getName())) {
            ObservableService service = crudServices.get(tClass.getName());
            if (!tClass.isInstance(service.getTarget()))
                return null;
            return service;
        }
        try {
            Constructor<? extends ICrudService<T,K>> cons = tClass.getConstructor(RepositoryFactory.class, ServiceFactory.class);
            crudServices.put(tClass.getName(), new ServerObservableService<>(cons.newInstance(repositoryFactory, this)));
            return getObservable(tClass);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ServerException("service not found!");
        }
    }
}
