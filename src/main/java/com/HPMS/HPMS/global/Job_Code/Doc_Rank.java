package com.HPMS.HPMS.global.Job_Code;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="DOC_RANK")
public class Doc_Rank {

    @Id
    @Size(max = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "ID")
    private Integer id;

    @Size(max = 30)
    @Column(name = "KOR_NAME", length = 30, nullable = false)
    private String korName;

    @Size(max = 30)
    @Column(name = "ENG_NAME", length = 50, nullable = false)
    private String engName;

    @Size(max = 30)
    @Column(name = "RANK", length = 30, nullable = false)
    private String rank;

}
