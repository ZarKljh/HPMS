package com.HPMS.HPMS.Patient.PatientM;


import com.HPMS.HPMS.Patient.PatientDTL.PatientDTL;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="TBL_PATIENT_M")
public class PatientM {
    //메인테이블 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "ID")
    private Integer id;
    //환자성명 성 한글표기
    @Column(name = "FIRST_NAME", nullable = false, length = 50)
    private String firstName;
    //환자성명 이름 한글표기
    @Column(name = "LAST_NAME", nullable = false, length = 50)
    private String lastName;
    //환사성명 중간이름
    @Column(name = "MIDDLE_NAME", length = 50)
    private String middleName;
    //여권성명 성
    @Column(name = "PASS_FIRST_NAME", length = 50)
    private String passFirstName;
    //여권성명 이름
    @Column(name = "PASS_LAST_NAME", length = 50)
    private String passLastName;
    //여권성명 중간이름
    @Column(name = "PASS_MIDDLE_NAME", length = 50)
    private String passMiddleName;
    //성별 M/F/X
    @Column(name = "GENDER", nullable = false, length = 1)
    private String gender;
    //생년월일 INT(8) → YYYYMMDD 형태
    @Column(name = "DAY_OF_BIRTH", nullable = false)
    private Integer dayOfBirth;
    //외국인구분 0=내국인, 1=외국인
    @Column(name = "FOREIGNER", nullable = false, length=1)
    private String foreigner;
    //여권번호 모두 대문자로 표기
    @Column(name = "PASSPORT", length = 20)
    private String passport;
    //환자신규등록일 자동입력
    @Column(name = "CREATEDATE", nullable = false)
    private LocalDateTime createDate;
    //환자정보수정일 자동입력
    @Column(name = "UPDATEDATE", nullable = false)
    private LocalDateTime updateDate;
    //환자종결 구분 1 = 종결
    @Column(name = "DEL_STATUS")
    private Integer delStatus;
    //환자상세테이블과 1대1매칭
    @OneToOne(mappedBy = "patientM", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PatientDTL patientDTL;
}