package com.k12.ter.DataSource_TER.model;

import java.util.Date;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "k12_ter_sched" ,schema = "ter")

public class K12_TerSched {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_start", nullable = false)
    private Date dateStart;

    @Column(name = "date_end", nullable = false)
    private Date dateEnd;

    @Column(name = "sy", nullable = false)
    private String sy;

    @Column(name ="status", nullable = false)
    private String status = "closed";

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String STATUS_CLOSED = "closed";

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String STATUS_OPEN = "open";
}
