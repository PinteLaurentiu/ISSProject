package com.iss.persistance;

import com.iss.database_connection.DatabaseException;
import com.iss.database_connection.HibernateDataSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class RepositoryFactory {
    private HashMap<String, IRepository> repositories;
    private HibernateDataSource dataSource;

    public RepositoryFactory( HibernateDataSource dataSource) {
        this.repositories = new HashMap<>();
        this.dataSource = dataSource;
    }

    public <T extends IRepository> T get(Class<T> tClass) {
        if (repositories.containsKey(tClass.getName())) {
            IRepository repository = repositories.get(tClass.getName());
            if (!tClass.isInstance(repository))
                return null;
            return tClass.cast(repository);
        }
        try {
           Constructor<T> cons = tClass.getConstructor(HibernateDataSource.class);
           repositories.put(tClass.getName(), cons.newInstance(dataSource));
           return get(tClass);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new DatabaseException("repo not found!");
        }
    }
}
