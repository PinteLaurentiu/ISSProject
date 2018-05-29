package com.iss.persistance;

import com.iss.database_connection.HibernateDataSource;
import com.iss.domain.Analiza;

public class AnalizaRepository extends HibernateRepository<Analiza, Integer> {
    public AnalizaRepository(HibernateDataSource dataSource) {
        super(dataSource, Analiza.class);
    }
}
