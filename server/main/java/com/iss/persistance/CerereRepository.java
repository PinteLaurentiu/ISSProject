package com.iss.persistance;

import com.iss.database_connection.HibernateDataSource;
import com.iss.domain.Cerere;

public class CerereRepository extends HibernateRepository<Cerere, Integer> {
    public CerereRepository(HibernateDataSource dataSource) {
        super(dataSource, Cerere.class);
    }
}
