package com.HPMS.HPMS.Doctor.DoctorDTL;

import com.HPMS.HPMS.Doctor.DoctorM.DoctorM;
import com.sun.jna.platform.win32.OaIdl;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_DOCTOR_DTL")
public class DoctorDTL {

    /** 고유ID: INT(4), PK, NOT NULL */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    /** 의사기본고유ID: INT(4), FK, NOT NULL, UNIQUE(1:1) */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DOCTOR_ID", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "FK_DOCTOR_DETAIL_MAIN"))
    private DoctorM doctorMain;

    /** 사진: VARCHAR(250), UNIQUE, NOT NULL (파일 경로/URL 권장) */
    @NotBlank
    @Size(max = 250)
    @Column(name = "IMG", length = 250, nullable = false, unique = true)
    private String image;

    /** 유저ID: VARCHAR(20), UNIQUE, NOT NULL */
    @NotBlank
    @Size(max = 20)
    @Column(name = "USR_ID", length = 20, nullable = false, unique = true)
    private String userId;

    /** 유저PASSWORD: VARCHAR(20), NOT NULL (실서비스는 해시 저장 권장) */
    @NotBlank
    @Size(max = 100)
    @Column(name = "USR_PWD", length = 100, nullable = false)
    private String userPassword;

    /** 사무실 전화번호: VARCHAR(20), UNIQUE, NOT NULL */
    @NotBlank
    @Size(max = 20)
    @Column(name = "OFF_TEL", length = 20, nullable = false, unique = true)
    private String officeTelephone;

    /** 비상연락처: VARCHAR(20), UNIQUE (NULL 허용) */
    @Size(max = 20)
    @Column(name = "EMGC_CNTC", length = 20, unique = true)
    private String emergencyContact;

    /** 비상연락처-이름: VARCHAR(50) */
    @Size(max = 50)
    @Column(name = "EMGC_F_NAME", length = 50)
    private String emergencyFirstName;

    /** 비상연락처-성: VARCHAR(50) */
    @Size(max = 50)
    @Column(name = "EMGC_L_NAME", length = 50)
    private String emergencyLastName;

    /** 비상연락처-중간이름: VARCHAR(50) */
    @Size(max = 50)
    @Column(name = "EMGC_M_NAME", length = 50)
    private String emergencyMiddleName;

    /** 비상연락처-관계: VARCHAR(10) */
    @Size(max = 10)
    @Column(name = "EMGC_REL", length = 10)
    private String emergencyRelation;

    /** 비상연락처-비고: VARCHAR(250) */
    @Size(max = 250)
    @Column(name = "EMGC_NOTE", length = 250)
    private String emergencyNote;

    /** 이메일: VARCHAR(250) */
    @Email
    @Size(max = 250)
    @Column(name = "EMAIL", length = 250)
    private String email;

    /** 우편번호: VARCHAR(6) */
    @Size(max = 6)
    @Column(name = "PCD", length = 6)
    private String postcode;

    /** 기본주소: VARCHAR(250) */
    @Size(max = 250)
    @Column(name = "DEF_ADD", length = 250)
    private String defaultAddress;

    /** 상세주소: VARCHAR(250) */
    @Size(max = 250)
    @Column(name = "DET_ADD", length = 250)
    private String detailedAddress;

    /** 의사면허번호: VARCHAR(10), UNIQUE, NOT NULL */
    @NotBlank
    @Size(max = 10)
    @Column(name = "RN_NO", length = 10, nullable = false, unique = true)
    private String registrationNumber;

    /** 학력: VARCHAR(100) */
    @Size(max = 100)
    @Column(name = "EDBC", length = 100)
    private String educationalBackground;

    /** 외국어: VARCHAR(250) */
    @Size(max = 250)
    @Column(name = "FL", length = 250)
    private String foreignLanguage;

    /** 기타 자격증: VARCHAR(250) */
    @Size(max = 250)
    @Column(name = "LI", length = 250)
    private String license;

    /** 전공과: VARCHAR(50), NOT NULL */
    @NotBlank
    @Size(max = 50)
    @Column(name = "MJR", length = 50, nullable = false)
    private String major;

    /** 근무경력: VARCHAR(250) */
    @Size(max = 250)
    @Column(name = "WORK_DOE", length = 250)
    private String workDependsOnExperience;

    /** 수상경력: VARCHAR(250) */
    @Size(max = 250)
    @Column(name = "AW", length = 250)
    private String awards;

    /** 학회활동: VARCHAR(250) */
    @Size(max = 250)
    @Column(name = "SCTA", length = 250)
    private String societyActivity;

    /** 학회인증: VARCHAR(250) */
    @Size(max = 250)
    @Column(name = "CRTOS", length = 250)
    private String certificateOfSociety;

    /** 병역: VARCHAR(10) */
    @Size(max = 10)
    @Column(name = "MS", length = 10)
    private String militaryService;

    /** 국적: VARCHAR(50) */
    @Size(max = 50)
    @Column(name = "NATN", length = 50)
    private String nationality;

    /** 장애여부: VARCHAR(1) */
    @Size(max = 1)
    @Column(name = "DSS", length = 1)
    private String disabilityStatus;

    /** 생성일시: DATETIME → LocalDateTime, NOT NULL */
    @NotNull
    @Column(name = "CREATE_DATE", nullable = false)
    private LocalDateTime createDate;

    /** 작성자: VARCHAR(250), NOT NULL */
    @NotBlank
    @Size(max = 250)
    @Column(name = "WRITER", length = 250, nullable = false)
    private String writer;

    /** 수정자: VARCHAR(250) */
    @Size(max = 250)
    @Column(name = "MODIFIER", length = 250)
    private String modifier;

    /** 수정일시: DATETIME → LocalDateTime */
    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;

    /** 비고: VARCHAR(255) */
    @Size(max = 255)
    @Column(name = "NOTE", length = 255)
    private String note;

    /** 생성/수정 자동 세팅 */
    @PrePersist
    protected void onCreate() {
        if (this.createDate == null) this.createDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifyDate = LocalDateTime.now();
    }
}