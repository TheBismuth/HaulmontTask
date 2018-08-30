package com.haulmont.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "doctors")
public class Doctor extends Person {
    private static final long serialVersionUID = 5923413937519593682L;
    private String specialization;

    public Doctor(Long id, String firstName, String lastName, String middleName, String specialization) {
        super(id, firstName, lastName, middleName);
        this.specialization = specialization;
    }

    public Doctor(String firstName, String lastName, String middleName, String specialization) {
        super(firstName, lastName, middleName);
        this.specialization = specialization;
    }

    public Doctor() {
    }


    @Column(name = "specialization")
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }


    @Override
    public String toString() {
        return  super.toString();

    }
}


