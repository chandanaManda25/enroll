package com.health.jpa;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "enroll", catalog = "healthcare")
@Data
public class Enroll implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "activation_status")
    private boolean activationStatus;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Version
    private Integer version;

    @OneToMany(mappedBy="id", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Dependent.class)
    private Set<Dependent> dependents;

    public Enroll() {
    }

    public Enroll(String name, boolean activationStatus, Date birthDate, String phoneNumber) {
        this.name = name;
        this.activationStatus = activationStatus;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }
}
