package com.haulmont.dao;

import com.haulmont.entity.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractDAO<E extends Person> {
    private Class<E> entityClass;
    private static final Logger logger = LoggerFactory.getLogger(AbstractDAO.class);

    Transaction tx = null;

    public AbstractDAO(Class<E> type) {
        this.entityClass = type;
    }

    /*
     *
     * Базовые CRUD операциии
     *
     * */

    //Create
    public void add(E entity) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            logger.info(entityClass.getSimpleName() + " was successfully saved.");
        }
    }

    //Read
    public E getById(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            E entity = session.load(entityClass, id);
            tx.commit();
            logger.info("" + entity);
            return entity;
        } catch (org.hibernate.ObjectNotFoundException e) {
            logger.error("Can't find " + entityClass.getSimpleName() + " with id " + id);
            return null;
        }
    }

    public List<E> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            List<E> entityList = session.createQuery("SELECT up FROM " + entityClass.getSimpleName() + " up").list();
            tx.commit();
            logger.info("" + entityList);
            return entityList;
        }
    }

    //update
    public void update(E entity) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            logger.info("" + entity);
        } catch (org.hibernate.TransientObjectException e) {
            logger.error("Can't update" + entityClass.getSimpleName());
        }
    }

    //delete
    public void removeById(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            E entity = session.load(entityClass, id);
            session.remove(entity);
            tx.commit();
            logger.info(entityClass.getSimpleName() + " was successfully deleted.");
        } catch (javax.persistence.EntityNotFoundException e) {
            logger.error("Can't find " + entityClass.getSimpleName() + " with id " + id);
        } catch (javax.persistence.PersistenceException e) {
            logger.error("Can't to remove " + entityClass.getSimpleName() + " inscribed in a recipe");
        }
    }
}
