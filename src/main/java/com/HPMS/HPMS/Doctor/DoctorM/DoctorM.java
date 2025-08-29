package com.HPMS.HPMS.Doctor.DoctorM;

import com.HPMS.HPMS.Doctor.DoctorDTL.DoctorDTL;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)   // ✅ 추가
@AllArgsConstructor
@Entity
@Table(name = "TBL_DOCTOR_M")
public class DoctorM {

    /** 고유ID: INT(4), PK, NOT NULL, AUTO_INCREMENT */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    /** 부서: VARCHAR(4), NOT NULL */
    @NotBlank
    @Size(max = 4)
    @Column(name = "DEPT", length = 4, nullable = false)
    private String department;

    /** 진료과: VARCHAR(50) */
    @Size(max = 50)
    @Column(name = "MED_DEP", length = 50)
    private String medicalDepartment;

    /** 직급: VARCHAR(4), NOT NULL */
    @NotBlank
    @Size(max = 4)
    @Column(name = "RANK", length = 4, nullable = false)
    private String rank;

    /** 직책: VARCHAR(4) */
    @Size(max = 4)
    @Column(name = "POS", length = 4)
    private String position;

    /** 성명-이름: VARCHAR(50), NOT NULL */
    @NotBlank
    @Size(max = 50)
    @Column(name = "FIRST_NAME", length = 50, nullable = false)
    private String firstName;

    /** 성명-성: VARCHAR(50), NOT NULL */
    @NotBlank
    @Size(max = 50)
    @Column(name = "LAST_NAME", length = 50, nullable = false)
    private String lastName;

    /** 성명-중간이름: VARCHAR(50) */
    @Size(max = 50)
    @Column(name = "MIDDLE_NAME", length = 50)
    private String middleName;

    /** 성별: VARCHAR(1), NOT NULL */
    @NotBlank
    @Size(max = 1)
    @Column(name = "GENDER", length = 1, nullable = false)
    private String gender;

    /** 생년월일: INT(8) → LocalDate, NOT NULL (예: 19990101 형태 대신 LocalDate 사용 권장) */
    @NotNull
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private LocalDate dateOfBirth;

    /** 전화번호: VARCHAR(20), NOT NULL */
    @NotBlank
    @Size(max = 20)
    @Column(name = "TEL", length = 20, nullable = false)
    private String telephone;

    /** 입사일: INT(8) → LocalDate, NOT NULL */
    @NotNull
    @Column(name = "HIREDATE", nullable = false)
    private LocalDate hireDate;

    /** 상태: VARCHAR(3), NOT NULL */
    @NotBlank
    @Size(max = 3)
    @Column(name = "STS", length = 3, nullable = false)
    private String status;

    /** 근무형태: VARCHAR(4), NOT NULL */
    @NotBlank
    @Size(max = 4)
    @Column(name = "WT", length = 4, nullable = false)
    private String workType;

    /** 1:1 역방향 매핑(선택) — 상세정보 */
    @OneToOne(mappedBy = "doctorMain", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DoctorDTL detail;
}