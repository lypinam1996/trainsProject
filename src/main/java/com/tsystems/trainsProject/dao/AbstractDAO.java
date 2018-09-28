package com.tsystems.trainsProject.dao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.ParameterizedType;

public class AbstractDAO<K,E> {

    private  final Class<K> persistanceClass;

    @Autowired
    SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public AbstractDAO(){
        this.persistanceClass=(Class<K>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];

    }

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public void persist (E entity){
        getSession().persist(entity);
    }

    public void delete(E entity){
        getSession().delete(entity);
    }

}