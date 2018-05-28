package com.iss.persistance;

import com.iss.database_connection.HibernateDataSource;
import com.iss.domain.Spital;

public class SpitalRepository extends HibernateRepository<Spital, Integer> {
    public SpitalRepository(HibernateDataSource dataSource) {
        super(dataSource, Spital.class);
    }
}
