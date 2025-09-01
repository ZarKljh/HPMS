package com.HPMS.HPMS.nurse.nurseinformation;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "NURSE_INFORMATION")
public class NurseInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "NURSE_ID", unique = true)
    private NurseMain nurseMain; //간호사 번호

    @Column(length = 50, name = "FIRST_NAME")
    private String firstName; //서브 이름

    @Column(length = 50, name = "LAST_NAME")
    private String lastName; //서브 성

    @Column(length = 50, name = "MIDDLE_NAME")
    private String middleName; //서브 중간이름

    @Column(length = 25, name = "TEL")
    private String tel; //전화번호

    @Column(length = 25, name = "EMGC_CNTC")
    private String emgcCntc; //비상연락처

    @Column(length = 50, name = "EMGC_F_NAME")
    private String emgcFName; //비상연락처 주인의 이름

    @Column(length = 50, name = "EMGC_L_NAME")
    private String emgcLName; //비상연락처 주인의 성

    @Column(length = 50, name = "EMGC_M_NAME")
    private String emgcMName; //비상연락처 주인의 중간이름

    @Column(length = 10, name = "EMGC_REL")
    private String emgcRel; //비상연락처 주인과의 관계 (부 / 모 / 친척 등)

    @Column(length = 250, name = "EMGC_NOTE")
    private String emgcNote; // 비상연락처 비고

    @Email
    @Column(length = 250, name = "EMAIL")
    private String email; //이메일

    @Column(name = "PCD")
    private Integer pcd; //우편번호

    @Column(length = 250, name = "DEF_ADD")
    private String defAdd; //기본주소

    @Column(length = 250, name = "DET_ADD")
    private String detAdd; //상세주소

    @Column(length = 6, name = "RN_NO")
    private String rnNo; //간호사 자격증 번호

    @Column(length = 50, name = "EDBC")
    private String edbc; //최종학력

    @Column(name = "GRAD_DATE")
    private LocalDate gradDate; //졸업날짜

    @Column(length = 250, name = "FL")
    private String fl; //외국어

    @Column(length = 10, name = "MS")
    private String ms; //병역

    @Column(length = 50, name = "NATN")
    private String natn; //국적

    @Column(length = 1, name = "DSS")
    private String dss; //장애여부

    @Column(length = 5, name = "CARR")
    private String carr; //경력(개월 수)

    @Column(length = 250, name = "PICTURE")
    private String picture; //사진 링크

    @Column(length = 250, name = "NOTE")
    private String note; //비고
}
