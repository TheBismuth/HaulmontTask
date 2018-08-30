package com.haulmont.dao;

import com.haulmont.entity.Doctor;

public class DoctorDAO extends AbstractDAO<Doctor> {

    public DoctorDAO(Class<Doctor> type) {
        super(type);
    }

//    //TODO
//    public Integer getStatistics(Long id) {
//        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
//            tx = session.beginTransaction();
//            Integer count = session.createQuery("select count(*) from Recipe r where r.doctor.id = '" + id + "'").list().size();
//        return count;}
//    }
}