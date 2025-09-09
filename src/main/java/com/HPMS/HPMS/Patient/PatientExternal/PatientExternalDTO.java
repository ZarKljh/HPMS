package com.HPMS.HPMS.Patient.PatientExternal;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PatientExternalDTO {

    //환자성명 이름 한글표기
    private String firstName;

    //환자성명 성 한글표기
    private String lastName;
    //환사성명 중간이름
    private String middleName;
    //환자성별
    private String gender;
    //환자 생년월일
    private Integer dayOfBirth;
    //환자 내외국인구분
    private String foreigner;

    //환자 휴대전화번호
    private String mobilePhone;
    //환자 이메일
    private String email;

    //환자 실거주지 주소 우편번호
    private String curHomePCD;
    //환자 실거주지 기본주소
    private String curHomeDefAdd;
    //환자 실거주지 상세주소
    private String curHomeDetAdd;

    //환자 직업
    private String occupation;
    //환자 마지막 방문일
    private LocalDateTime lastVisitDate;
    //환자 국적 여권기준
    private String natn;


}
