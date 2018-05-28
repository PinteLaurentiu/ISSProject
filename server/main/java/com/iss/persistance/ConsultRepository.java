package com.iss.persistance;

import com.iss.database_connection.DatabaseException;
import com.iss.database_connection.HibernateDataSource;
import com.iss.domain.Consult;
import com.iss.domain.Donare;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ConsultRepository extends HibernateRepository<Consult, Integer> {
    public ConsultRepository(HibernateDataSource dataSource) {
        super(dataSource, Consult.class);
    }

}
