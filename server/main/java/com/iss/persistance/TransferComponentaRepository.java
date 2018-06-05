package com.iss.persistance;

import com.iss.database_connection.HibernateDataSource;
import com.iss.domain.TransferComponenta;

public class TransferComponentaRepository extends HibernateRepository<TransferComponenta, Integer> {
    public TransferComponentaRepository(HibernateDataSource dataSource) {
        super(dataSource, TransferComponenta.class);
    }
}
