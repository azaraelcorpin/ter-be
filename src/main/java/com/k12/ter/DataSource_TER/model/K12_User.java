package com.k12.ter.DataSource_TER.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "k12_user" ,schema = "ter")

public class K12_User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "position", nullable = false)
    private String position;

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String POSITION_ADMIN = "Admin";

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String POSITION_DEAN = "Dean";

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String POSITION_CHAIRPERSON = "Chairperson";
}
