package com.k12.ter.DataSource_TER.model;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "k12_registration" ,schema = "test")

public class K12_Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String subjcode;

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String STATUS_APPLIED = "APPLIED";

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String STATUS_INVALID = "INVALID";

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String STATUS_CERTIFIED = "CERTIFIED";

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String STATUS_RECOMMENDED = "RECOMMENDED";

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String STATUS_APPROVED = "APPROVED";
}
