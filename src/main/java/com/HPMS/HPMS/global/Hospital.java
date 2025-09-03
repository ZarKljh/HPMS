package com.HPMS.HPMS.global;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="HOSPITAL")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "ID")
    private Integer id;


    @Enumerated(EnumType.STRING)
    @Column(name = "HOSPITAL_GROUP", nullable = false, columnDefinition = "ENUM('GP','SH','GH') COMMENT '병원 분류: GP(1차), SH(2차), GH(3차)'")
    private HospitalGroup hospitalGroup;



    @Column(name = "H_NAME", nullable = false, length = 250, columnDefinition = "VARCHAR(250) COMMENT '병원 이름'")
    private String hName;


    @Column(name = "H_PCD", nullable = false, columnDefinition = "INT(6) COMMENT '병원 우편번호'")
    private Integer hPcd;


    @Column(name = "DEF_ADD", nullable = false, length = 250, columnDefinition = "VARCHAR(250) COMMENT '병원 기본주소'")
    private String defAdd;


    @Column(name = "DET_ADD", length = 250, columnDefinition = "VARCHAR(250) COMMENT '병원 상세주소'")
    private String detAdd;


    @Column(name = "GDN", length = 12, columnDefinition = "VARCHAR(12) COMMENT '병원 대표번호'")
    private String gdn;


    @Column(name = "DOCTOR", columnDefinition = "INT(4) COMMENT '근무하는 의사의 수'")
    private Integer doctor;


    @Column(name = "NURSE", columnDefinition = "INT(4) COMMENT '근무하는 간호사의 수'")
    private Integer nurse;


    @Column(name = "STS", length = 10, columnDefinition = "VARCHAR(10) COMMENT '상태(운영중, 폐업 등)'")
    private String sts;


    @Column(name = "NOTE", length = 250, columnDefinition = "VARCHAR(250) COMMENT '비고'")
    private String note;




    // 병원 분류 ENUM
    public enum HospitalGroup {
        GP, SH, GH
    }
}
