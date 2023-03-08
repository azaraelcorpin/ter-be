package com.k12.ter.DataSource_TER.model;

import java.util.Date;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "k12_policyauth" ,schema = "ter")

public class K12_PolicyAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "date_clicked", nullable = false)
    private Date date_clicked;
}
