package com.HPMS.HPMS.global;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "MEDICAL_DEPARTMENT",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"KOREAN_NAME", "CODE"})
        }
)
public class MedicalDepartment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false, columnDefinition = "INT AUTO_INCREMENT COMMENT '고유 ID'")
    private Integer id;


    @Column(name = "KOREAN_NAME", nullable = false, length = 100, columnDefinition = "VARCHAR(100) COMMENT '과목명(한글)'")
    private String koreanName;


    @Column(name = "CODE", nullable = false, length = 20, columnDefinition = "VARCHAR(20) COMMENT '과목 코드 (약어)'")
    private String code;


    @Column(name = "ENGLISH_NAME", nullable = false, length = 200, columnDefinition = "VARCHAR(200) COMMENT '과목명(영문)'")
    private String englishName;
}

