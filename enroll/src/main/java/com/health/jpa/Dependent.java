package com.health.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "dependent", catalog = "healthcare")
@Data public class Dependent implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Date birthDate;

    public Dependent() {
        super();
    }
}
