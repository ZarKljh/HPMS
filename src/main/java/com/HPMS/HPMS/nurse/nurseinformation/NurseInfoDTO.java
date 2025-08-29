package com.HPMS.HPMS.nurse.nurseinformation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NurseInfoDTO {

    // nurseMain 정보
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dept;
    private String rank;
    private String gender;
    private Integer dateOfBirth;
    private Integer hireDate;
    private String sts;
    private String wt;
    private String writer;
    private LocalDateTime createDate;
    private String modifier;
    private LocalDateTime modifyDate;

    // nurseInformation 정보
    private String tel;
    private String emgcCntc;
    private String emgcFName;
    private String emgcMName;
    private String emgcLName;
    private String emgcRel;
    private String emgcNote;
    private String email;
    private Integer pcd;
    private String defAdd;
    private String detAdd;
    private String rnNo;
    private String edbc;
    private Integer gradDate;
    private String fl;
    private String ms;
    private String natn;
    private String dss;
    private String carr;
    private String picture;
    private String note;
}
