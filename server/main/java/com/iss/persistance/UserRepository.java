package com.iss.persistance;

import com.iss.database_connection.HibernateDataSource;
import com.iss.domain.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;

import javax.persistence.ParameterMode;

public class UserRepository extends HibernateRepository<User, Integer> {
    public UserRepository(HibernateDataSource dataSource) {
        super(dataSource, User.class);
    }

    public boolean login(String user, String password){
        try(Session session = dataSource.getSession()){
            Transaction transaction = session.beginTransaction();
            ProcedureCall procedureCall = session.createStoredProcedureCall("login");
            procedureCall.registerParameter(0,String.class, ParameterMode.IN).bindValue(user);
            procedureCall.registerParameter(1, String.class, ParameterMode.IN).bindValue(password);
            boolean logged = ((java.math.BigInteger)procedureCall.getSingleResult()).intValue() == 1;

            transaction.commit();
            return logged;
        }
        catch (Exception ex){
//            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void put(User elem) {
        super.put(elem);
        try(Session session = dataSource.getSession()){
            Transaction transaction = session.beginTransaction();
            ProcedureCall procedureCall = session.createStoredProcedureCall("setPassword");
            procedureCall.registerParameter(0,Integer.class, ParameterMode.IN).bindValue(elem.getId());
            procedureCall.registerParameter(1, String.class, ParameterMode.IN).bindValue(elem.getPassword());
            procedureCall.execute();
            transaction.commit();
        }
        catch (Exception ignored){

        }
    }
}
