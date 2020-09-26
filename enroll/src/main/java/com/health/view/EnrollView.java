package com.health.view;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.health.jpa.Dependent;

public class EnrollView {
    private Long id;
    private String name;
    private boolean activationStatus;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
    private String phoneNumber;
    private Set<DependentView> dependents;
    private Integer version;

    public EnrollView() {
    }

    public EnrollView( String name, boolean activationStatus, Date birthDate, String phoneNumber) {
        this.name = name;
        this.activationStatus = activationStatus;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }
    public EnrollView(Long id, String name, boolean activationStatus, Date birthDate, String phoneNumber, Integer version) {
        this.id = id;
        this.name = name;
        this.activationStatus = activationStatus;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(boolean activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<DependentView> getDependents() {
        return dependents;
    }

    public void setDependents(Set<DependentView> dependents) {
        this.dependents = dependents;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
