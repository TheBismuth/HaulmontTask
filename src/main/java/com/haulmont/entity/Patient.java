package com.haulmont.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "patients")
public class Patient extends Person {
    private static final long serialVersionUID = 7930743293226270804L;
    private String cellPhone;

    public Patient(Long id, String firstName, String lastName, String middleName, String cellPhone) {
        super(id, firstName, lastName, middleName);
        this.cellPhone = cellPhone;
    }

    public Patient(String firstName, String lastName, String middleName, String cellPhone) {
        super(firstName, lastName, middleName);
        this.cellPhone = cellPhone;
    }

    public Patient() {
    }

    @Column(name = "phone_number")
    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}

