package com.k12.ter.DataSource_TER.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "test_Model" ,schema = "test")

public class testModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description2")
    private String description2String;
}
