package com.HPMS.HPMS.nurse.nurseinformation;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "NURSE_INFORMATION")
public class NurseInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "NURSE_ID")
    private NurseMain nurseId;

    @Column(length = 50, name = "FIRST_NAME")
    private String firstName;

    @Column(length = 50, name = "LAST_NAME")
    private String lastName;

    @Column(length = 50, name = "MIDDLE_NAME")
    private String middleName;

    @Column(length = 25, name = "TEL")
    private String tel;

    @Column(length = 25, name = "EMGC_CNTC")
    private String emgcCntc;

    @Column(length = 50, name = "EMGC_F_NAME")
    private String emgcFName;

    @Column(length = 50, name = "EMGC_L_NAME")
    private String emgcLName;

    @Column(length = 50, name = "EMGC_M_NAME")
    private String emgcMName;

    @Column(length = 10, name = "EMGC_REL")
    private String EmgcRel;

    @Column(length = 250, name = "EMGC_NOTE")
    private String emgcNote;

    @Column(length = 250, name = "EMAIL")
    private String email;

    @Column(name = "PCD")
    private Integer pcd;

    @Column(length = 250, name = "DEF_ADD")
    private String defAdd;

    @Column(length = 250, name = "DET_ADD")
    private String detAdd;

    @Column(length = 6, name = "RN_NO")
    private String rnNo;

    @Column(length = 50, name = "EDBC")
    private String edbc;

    @Column(name = "GRAD_DATE")
    private Integer gradDate;

    @Column(length = 250, name = "FL")
    private String fl;

    @Column(length = 10, name = "MS")
    private String ms;

    @Column(length = 50, name = "NATN")
    private String natn;

    @Column(length = 1, name = "DSS")
    private String dss;

    @Column(length = 5, name = "CARR")
    private String carr;

    @Column(length = 250, name = "PICTURE")
    private String picture;

    @Column(length = 250, name = "NOTE")
    private String note;
}
