package com.iss.persistance;

import com.iss.database_connection.HibernateDataSource;
import com.iss.domain.Donare;

public class DonareRepository extends HibernateRepository<Donare, Integer> {
    public DonareRepository(HibernateDataSource dataSource) {
        super(dataSource, Donare.class);
    }
}
