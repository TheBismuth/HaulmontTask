package com.haulmont.dao;

import com.haulmont.entity.Patient;

public class PatientDAO extends AbstractDAO<Patient> {

    public PatientDAO(Class<Patient> type) {
        super(type);
    }
}