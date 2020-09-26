package com.health.view;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class DependentView {
    private Long id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    public DependentView() {
    }
    public DependentView(String name, Date birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public DependentView(Long id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}
