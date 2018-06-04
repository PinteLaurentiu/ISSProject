package com.iss.persistance;

import com.iss.database_connection.HibernateDataSource;
import com.iss.domain.ComponentaSange;

public class ComponentaSangeRepository extends HibernateRepository<ComponentaSange, Integer> {
    public ComponentaSangeRepository(HibernateDataSource dataSource) {
        super(dataSource, ComponentaSange.class);
    }
}
