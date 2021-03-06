package com.iss.persistance;

import com.iss.database_connection.HibernateDataSource;
import com.iss.domain.Transfer;

public class TransferRepository extends HibernateRepository<Transfer, Integer>{
    public TransferRepository(HibernateDataSource dataSource) {
        super(dataSource, Transfer.class);
    }
}
