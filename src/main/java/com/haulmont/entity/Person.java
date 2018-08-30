package com.haulmont.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;


@MappedSuperclass
public abstract class Person implements Serializable {
    private static final long serialVersionUID = 7794401440615220616L;
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;

    public Person(Long id, String firstName, String lastName, String middleName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    public Person(String firstName, String lastName, String middleName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    public Person() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    @Column(name = "middle_name")
    public String getMiddleName() {
        return middleName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + middleName;
    }
}
