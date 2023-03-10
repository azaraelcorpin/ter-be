package com.k12.ter.DataSource_TER.model;

import java.util.Date;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "k12_questionnaire" ,schema = "ter")

public class K12_Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "eval_type", nullable = false)
    private String evalType;

    @Column(name = "q_number", nullable = false)
    private int qstnNumber;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name ="sy", nullable = false)
    private String sy;

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String EVAL_TYPE_HEAD = "H";

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String EVAL_TYPE_PEER = "P";

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String EVAL_TYPE_STUDENT = "S";
}
