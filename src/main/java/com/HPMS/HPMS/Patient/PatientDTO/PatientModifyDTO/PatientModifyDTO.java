package com.HPMS.HPMS.Patient.PatientDTO.PatientModifyDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PatientModifyDTO {

    private Integer id;
    private String delStatus; // 1 = 종결
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private LocalDate birth;         // 생년월일 or 나이
    private String gender;
    private String foreigner;     // 내/외국인 여부
    private String firstName;
    private String lastName;
    private String middleName; // 성명 (LastName + FirstName)
    private String passport;
    private String passFirstName; // 여권성명 (PassLastName + PassFirstName)
    private String passLastName;
    private String passMiddleName;

    private String mobilePhone;
    private String homePhone;
    private String officePhone;
    private String email;
    private String fax;

    private String guardianName; // 보호자성명 (guadianLastName + guardianFirstName)
    private String guardianMiddleName;
    private String guardianTel;
    private String guardianRelation;

    private String homePcd;
    private String homeDefAdd;
    private String homeDetAdd;
    private String regPcd;
    private String regDefAdd;
    private String regDetAdd;

    private String occupation;
    private String natn;
    private LocalDateTime lastVisitDate;
    private String note;

}
