package com.iss.persistance;

import com.iss.database_connection.HibernateDataSource;
import com.iss.domain.PungaSange;

public class PungaSangeRepository extends HibernateRepository<PungaSange, Integer> {
    public PungaSangeRepository(HibernateDataSource dataSource) {
        super(dataSource, PungaSange.class);
    }
}
