package com.HPMS.HPMS.global;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "HOSPITAL_ADMINISTRATION_DEPT")
public class HospitalAdministrationDept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 고유 ID



    @Column(name = "CODE", nullable = false, unique = true, length = 10)
    private String code; // 부서 코드


    @Column(name = "DISPLAY_NAME", nullable = false, length = 100)
    private String displayName; // 부서 국문명


    @Column(name = "ENG_NAME", nullable = false, length = 200)
    private String engName; // 부서 영문명


    @Column(name = "CATEGORY", length = 50, columnDefinition = "VARCHAR(50) DEFAULT '병원 행정'")
    private String category; // 구분 카테고리
}


