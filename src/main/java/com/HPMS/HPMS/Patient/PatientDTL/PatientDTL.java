package com.HPMS.HPMS.Patient.PatientDTL;

import com.HPMS.HPMS.Patient.PatientM.PatientM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="TBL_PATIENT_DTL")
public class PatientDTL {
    //환자상세테이블 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    //환자메인테이블과 1대1 매칭
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PA_ID", nullable = false)
    private PatientM patientM;
    //환자휴대전환번호 - 없이 입력
    @Column(name = "MO_PHONE_NUM", nullable = false, length = 20)
    private String mobilePhone;
    //환자집전화번호 - 없이 입력
    @Column(name = "HOME_PHONE_NUM", length = 20)
    private String homePhone;
    //환자직장전화번호 - 없이 입력
    @Column(name = "OFFICE_PHONE_NUM", length = 20)
    private String officePhone;
    //환자 email 주소
    @Column(name = "EMAIL", length = 50)
    private String email;
    //환자 fax 번호 - 없이 지역번호부터 입력
    @Column(name = "FAX", length = 20)
    private String fax;
    //보호자 연락처 - 없이 입력
    @Column(name = "GURAD_TEL", nullable = false, length = 20)
    private String guardianTel;
    //환자와 보호자의 관계
    @Column(name = "GURAD_RELA", nullable = false, length = 20)
    private String guardianRelation;
    //보호자성명 성
    @Column(name = "GURAD_FIRST_NAME", nullable = false, length = 50)
    private String guardianFirstName;
    //보호자성명 이름
    @Column(name = "GURAD_LAST_NAME", nullable = false, length = 50)
    private String guardianLastName;
    //보호자성명 중간이름
    @Column(name = "GURAD_MIDDLE_NAME", length = 50)
    private String guardianMiddleName;
    //환자 실거주지 주소 우편번호
    @Column(name = "CUR_HOME_PCD", length = 10)
    private String curHomePCD;
    //환자 실거주지 기본주소
    @Column(name = "CUR_HOME_DEF_ADD", length = 255)
    private String curHomeDefAdd;
    //환자 실거주지 상세주소
    @Column(name = "CUR_HOME_DET_ADD", length = 255)
    private String curHomeDetAdd;
    //환자 등록주소지 우편번호
    @Column(name = "REG_HOME_PCD", length = 10)
    private String regHomePCD;
    //환자 등록주소지 기본주소
    @Column(name = "REG_HOME_DEF_ADD", length = 255)
    private String regHomeDefAdd;
    //환자 등록주소지 상세주소
    @Column(name = "REG_HOME_DET_ADD", length = 255)
    private String regHomeDetAdd;
    //환자 직업
    @Column(name = "OCCUPATION", length = 50)
    private String occupation;
    //환자 마지막 방문일
    @Column(name = "LASTVISIT_DATE", nullable = false,
            columnDefinition = "DATETIME DEFAULT NOW()")
    private java.time.LocalDateTime lastVisitDate;
    //환자 국적 여권기준
    @Column(name = "NATN", length = 50)
    private String natn;
    //기타설명 및 비고
    @Column(name = "NOTE", columnDefinition = "TEXT")
    private String note;
}