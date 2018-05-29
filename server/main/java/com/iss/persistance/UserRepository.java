package com.iss.persistance;

import com.iss.database_connection.HibernateDataSource;
import com.iss.domain.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;

import javax.persistence.ParameterMode;
import javax.persistence.Query;

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
        User existent = getByEmail(elem.getEmail());
        if (existent != null) {
            update(elem);
            return;
        }
        super.put(elem);
        try(Session session = dataSource.getSession()){
            Transaction transaction = session.beginTransaction();
            ProcedureCall procedureCall = session.createStoredProcedureCall("setPassword");
            procedureCall.registerParameter(0,Integer.class, ParameterMode.IN).bindValue(elem.getId());
            procedureCall.registerParameter(1, String.class, ParameterMode.IN).bindValue(elem.getPassword());
            procedureCall.execute();
            transaction.commit();
        }
        catch (Exception ignored){}
    }

    private void update(User elem) {
        try(Session session = dataSource.getSession()){
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM User where email = '" + elem.getEmail() +"'");
            User curentUser = (User) query.getSingleResult();
            curentUser.getRoles().clear();
            curentUser.getRoles().addAll(elem.getRoles());
            transaction.commit();
        }
    }

    public void activation(int id, String uid) {
        try(Session session = dataSource.getSession()){
            Transaction transaction = session.beginTransaction();
            ProcedureCall procedureCall = session.createStoredProcedureCall("activate");
            procedureCall.registerParameter(0,Integer.class, ParameterMode.IN).bindValue(id);
            procedureCall.registerParameter(1, String.class, ParameterMode.IN).bindValue(uid);
            procedureCall.registerParameter(2, Boolean.class, ParameterMode.IN).bindValue(true);
            procedureCall.execute();
            transaction.commit();
        }
        catch (Exception ignored){}
    }

    public User getByEmail(String email) {
        for (User user: getAll()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}
