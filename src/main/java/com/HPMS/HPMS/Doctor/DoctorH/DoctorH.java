package com.HPMS.HPMS.Doctor.DoctorH;

import com.HPMS.HPMS.Doctor.DoctorDTL.DoctorDTL;
import com.HPMS.HPMS.Doctor.DoctorM.DoctorM;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // ✅ JPA 기본 생성자 추가
@AllArgsConstructor
@Entity
@Table(name = "TBL_DOCTOR_H")
public class DoctorH {

    /** 고유ID: INT(4), PK, NOT NULL */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    /** 부서: VARCHAR(4), NOT NULL */
    @NotBlank
    @Size(max = 4)
    @Column(name = "DEPT", length = 4)
    private String department;

    /** 진료과: VARCHAR(50) */
    @Size(max = 50)
    @Column(name = "MED_DEP", length = 50)
    private String medicalDepartment;

    /** 직급: VARCHAR(4), NOT NULL */
    @NotBlank
    @Size(max = 4)
    @Column(name = "RANK", length = 4)
    private String rank;


    /** 성명-이름: VARCHAR(50), NOT NULL */
    @NotBlank
    @Size(max = 50)
    @Column(name = "FIRST_NAME", length = 50)
    private String firstName;

    /** 성명-성: VARCHAR(50), NOT NULL */
    @NotBlank
    @Size(max = 50)
    @Column(name = "LAST_NAME", length = 50)
    private String lastName;

    /** 성명-중간이름: VARCHAR(50) */
    @Size(max = 50)
    @Column(name = "MIDDLE_NAME", length = 50)
    private String middleName;

    /** 성별: VARCHAR(1), NOT NULL */
    @NotBlank
    @Size(max = 1)
    @Column(name = "GENDER", length = 1)
    private String gender;

    /** 생년월일: INT(8) → LocalDate, NOT NULL (예: 19990101 형태 대신 LocalDate 사용 권장) */
    @NotNull
    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;

    /** 전화번호: VARCHAR(20), NOT NULL */
    @NotBlank
    @Size(max = 20)
    @Column(name = "TEL", length = 20)
    private String telephone;

    /** 입사일: INT(8) → LocalDate, NOT NULL */
    @NotNull
    @Column(name = "HIREDATE")
    private LocalDate hireDate;

    /** 상태: VARCHAR(3), NOT NULL */
    @NotBlank
    @Size(max = 3)
    @Column(name = "STS", length = 3)
    private String status;

    /** 근무형태: VARCHAR(4), NOT NULL */
    @NotBlank
    @Size(max = 4)
    @Column(name = "WT", length = 4)
    private String workType;

    /** 사진: VARCHAR(250), UNIQUE, NOT NULL (파일 경로/URL 권장) */
    @NotBlank
    @Size(max = 250)
    @Column(name = "IMG", length = 250)
    private String image;

    /** 유저ID: VARCHAR(20), UNIQUE, NOT NULL */
    @NotBlank
    @Size(max = 20)
    @Column(name = "USR_ID", length = 20)
    private String userId;

    /** 비상연락처: VARCHAR(20), UNIQUE (NULL 허용) */
    @Size(max = 20)
    @Column(name = "EMGC_CNTC", length = 20)
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


    /** 의사면허번호: VARCHAR(10), UNIQUE, NOT NULL */
    @NotBlank
    @Size(max = 10)
    @Column(name = "RN_NO", length = 10)
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
    @Column(name = "MJR", length = 50)
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
    @Column(name = "CREATE_DATE",nullable = false)
    private LocalDateTime createDate;

    /** 작성자: VARCHAR(250), NOT NULL */
    @Size(max = 250)
    @Column(name = "WRITER", nullable = false,length = 250)
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DOCTOR_ID", nullable = false)
    private DoctorM doctorMain;

    // 안전망
    @PrePersist
    void onPrePersist() {
        if (createDate == null) createDate = LocalDateTime.now();
        if (writer == null || writer.isBlank()) writer = "system";
    }

    public static DoctorH snapshotOf(DoctorM m, DoctorDTL d) {
        return DoctorH.builder()
                .doctorMain(m)
                .createDate(LocalDateTime.now())     // NOT NULL 채우기
                .writer("system")
                // 메인
                .department(m.getDepartment())
                .medicalDepartment(m.getMedicalDepartment())
                .rank(m.getRank())
                .firstName(m.getFirstName())
                .lastName(m.getLastName())
                .middleName(m.getMiddleName())
                .gender(m.getGender())
                .dateOfBirth(m.getDateOfBirth())
                .telephone(m.getTelephone())
                .hireDate(m.getHireDate())
                .status(m.getStatus())
                .workType(m.getWorkType())
                // 디테일 (d가 없을 수도 있으면 null 체크)
                .image(d != null ? d.getImage() : null)
                .userId(d != null ? d.getUserId() : null)
                .emergencyContact(d != null ? d.getEmergencyContact() : null)
                .emergencyFirstName(d != null ? d.getEmergencyFirstName() : null)
                .emergencyLastName(d != null ? d.getEmergencyLastName() : null)
                .emergencyMiddleName(d != null ? d.getEmergencyMiddleName() : null)
                .emergencyRelation(d != null ? d.getEmergencyRelation() : null)
                .emergencyNote(d != null ? d.getEmergencyNote() : null)
                .registrationNumber(d != null ? d.getRegistrationNumber() : null)
                .educationalBackground(d != null ? d.getEducationalBackground() : null)
                .foreignLanguage(d != null ? d.getForeignLanguage() : null)
                .license(d != null ? d.getLicense() : null)
                .major(d != null ? d.getMajor() : null)
                .workDependsOnExperience(d != null ? d.getWorkDependsOnExperience() : null)
                .awards(d != null ? d.getAwards() : null)
                .societyActivity(d != null ? d.getSocietyActivity() : null)
                .certificateOfSociety(d != null ? d.getCertificateOfSociety() : null)
                .militaryService(d != null ? d.getMilitaryService() : null)
                .nationality(d != null ? d.getNationality() : null)
                .disabilityStatus(d != null ? d.getDisabilityStatus() : null)
                .note(d != null ? d.getNote() : null)
                .build();
    }
}
