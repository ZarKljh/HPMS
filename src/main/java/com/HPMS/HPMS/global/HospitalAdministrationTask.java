package com.HPMS.HPMS.global;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "HOSPITAL_ADMINISTRATION_TASK")
public class HospitalAdministrationTask {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 고유 ID


    // 부서 코드 (FK) - HospitalAdministrationDept의 CODE와 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPT_CODE", referencedColumnName = "CODE", nullable = false)
    private HospitalAdministrationDept department;



    @Column(name = "CODE", nullable = false, length = 10)
    private String code; // 업무 코드


    @Column(name = "DISPLAY_NAME", nullable = false, length = 100)
    private String displayName; // 업무 국문명


    @Column(name = "ENG_NAME", nullable = false, length = 200)
    private String engName; // 업무 영문명
}


