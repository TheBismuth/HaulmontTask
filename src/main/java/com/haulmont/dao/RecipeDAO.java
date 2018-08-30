package com.haulmont.dao;

import com.haulmont.entity.Recipe;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecipeDAO {
    Transaction tx = null;
    private static final Logger logger = LoggerFactory.getLogger(AbstractDAO.class);

    public void add(Recipe recipe) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(recipe);
            tx.commit();
            logger.info(Recipe.class.getSimpleName() + " was successfully saved.");
        }
    }

    public Recipe getById(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Recipe recipe = session.load(Recipe.class, id);
            tx.commit();
            logger.info("" + recipe);
            return recipe;
        } catch (org.hibernate.ObjectNotFoundException e) {
            logger.error("Can't find " + Recipe.class.getSimpleName() + " with id " + id);
            return null;
        }
    }

    public List<Recipe> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            logger.info("" + session.createQuery("SELECT up FROM " + Recipe.class.getSimpleName() + " up").list());
            return session.createQuery("SELECT up FROM " + Recipe.class.getSimpleName() + " up").list();
        }
    }

    public void update(Recipe recipe) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(recipe);
            tx.commit();
        } catch (org.hibernate.TransientObjectException e) {
            logger.error("Can't update" + Recipe.class.getSimpleName());
        }
    }

    public void removeById(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Recipe recipe = session.byId(Recipe.class).load(id);
            session.delete(recipe);
            tx.commit();
            logger.error(Recipe.class.getSimpleName() + " was successfully deleted.");
        } catch (javax.persistence.EntityNotFoundException | java.lang.IllegalArgumentException e) {
            logger.error("Can't find " + Recipe.class.getSimpleName() + " with id " + id);
        }
    }

    //Фильтрация рецептов

    public List<Recipe> findByparam(String desc) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            logger.info("" + session.createQuery("select r from com.haulmont.entity.Recipe r where r.description like '%" + desc + "%'", Recipe.class).list());
            return session.createQuery("select r from com.haulmont.entity.Recipe r where r.description like '%" + desc + "%'", Recipe.class).list();
        }
    }
//     TODO
//    public List<Recipe> findByPriority(String prior) {
//        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
//            tx = session.beginTransaction();
//            List<Recipe> recipes = session.createQuery("select r from com.haulmont.entity.Recipe r where r.priority like '%" + prior +  "%'", Recipe.class).list();
//            logger.info("" + recipes);
//            return recipes;
//        }
//    }

    public List<Recipe> findByPatient(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            logger.info("" + session.createQuery("select r from com.haulmont.entity.Recipe r where r.patient.id like '%" + id + "%'", Recipe.class).list());
            return session.createQuery("select r from com.haulmont.entity.Recipe r where r.patient.id like '%" + id + "%'", Recipe.class).list();
        }
    }
}




