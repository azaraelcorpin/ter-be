package com.k12.ter.DataSource_TER.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "leave_type" ,schema = "test",uniqueConstraints={@UniqueConstraint(columnNames={"code"})})

public class LeaveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "number_of_days")
    private int numberOfDays;
        
}
