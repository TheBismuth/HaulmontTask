package com.haulmont.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "recipes")
public class Recipe implements Serializable {
    private static final long serialVersionUID = 8920874998239748663L;
    private Long id;
    private String description;
    private Patient patient;
    private Doctor doctor;
    private LocalDate creationDate;
    private LocalDate runOfValidity;
    private Priority priority;

    public Recipe(Long id, String description, Patient patient, Doctor doctor, LocalDate creationDate,
                  LocalDate runOfValidity, Priority priority) {
        this.id = id;
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.creationDate = creationDate;
        this.runOfValidity = runOfValidity;
        this.priority = priority;
    }

    public Recipe(String description, Patient patient, Doctor doctor, LocalDate creationDate,
                  LocalDate runOfValidity, Priority priority) {
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.creationDate = creationDate;
        this.runOfValidity = runOfValidity;
        this.priority = priority;
    }

    public Recipe() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @ManyToOne(optional = false, targetEntity = Patient.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "patient")
    public Patient getPatient() {
        return patient;
    }

    @ManyToOne(optional = false, targetEntity = Doctor.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "doctor")
    public Doctor getDoctor() {
        return doctor;
    }

    @Column(name = "creationDate")
    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Column(name = "runOfValidity")
    public LocalDate getRunOfValidity() {
        return runOfValidity;
    }

    @Column(name = "priority")
    public Priority getPriority() {
        return priority;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setRunOfValidity(LocalDate runOfValidity) {
        this.runOfValidity = runOfValidity;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public enum Priority {
        NORMAL("Нормальный"),
        CITO("Срочный"),
        STATIM("Немедленный");

        private String s;

        Priority(String s) {
            this.s = s;
        }

        public String getS() {
            return s;
        }
    }

    @Override
    public String toString() {
        return "Recipe:\n" +
                "id = " + id + "\n" +
                "description = " + description + "\n" +
                "patient = " + patient + "\n" +
                "doctor = " + doctor + "\n" +
                "creationDate = " + creationDate + "\n" +
                "runOfValidity = " + runOfValidity + "\n" +
                "priority = " + priority + "\n";
    }
}


