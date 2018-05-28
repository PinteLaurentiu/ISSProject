package com.iss.persistance;

import com.iss.database_connection.DatabaseException;
import com.iss.database_connection.HibernateDataSource;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.Iterator;

public class HibernateRepository<T,K extends Serializable> implements IRepository<T,K> {

    protected final HibernateDataSource dataSource;
    private final Class<T> tClass;

    public HibernateRepository(HibernateDataSource dataSource, Class<T> tClass) {
        this.dataSource = dataSource;
        this.tClass = tClass;
    }

    @Override
    public void put(T elem) {
        try(Session session = dataSource.getSession()){
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(elem);
            transaction.commit();
        }catch (Exception e){
            throw new DatabaseException("Couldn't update/remove");
        }
    }

    @Override
    public void remove(K key) {
        try(Session session = dataSource.getSession()){
            Transaction transaction = session.beginTransaction();
            T object = session.load(tClass, key);
            session.remove(object);
            transaction.commit();
        }catch (Exception e){
            throw new DatabaseException("Could not remove");
        }
    }

    @Override
    public Iterable<T> getAll() {
        return getAll(-1,0);
    }

    @Override
    public Iterable<T> getAll(int count, int offset) {
        try(Session session = dataSource.getSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from " + tClass.getSimpleName());
            if (count != -1) {
                query.setFirstResult(offset*count);
                query.setMaxResults(count);
            }
            transaction.commit();
            return query.getResultList();
        }
        catch (Exception e){
            throw new DatabaseException("Couldn't get all the objects!");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return getAll().iterator();
    }

    @Override
    public int count() {
        try(Session session = dataSource.getSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("select count(*) from " + tClass.getSimpleName());
            transaction.commit();
            return ((Number)query.getSingleResult()).intValue();
        }
        catch (Exception e){
            throw new DatabaseException("Couldn't count all the objects!");
        }
    }
}
