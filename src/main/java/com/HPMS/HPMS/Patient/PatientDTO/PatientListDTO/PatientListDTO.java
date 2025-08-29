package com.HPMS.HPMS.Patient.PatientDTO.PatientListDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PatientListDTO {
    private Integer id;
    private String name;          // 성명 (LastName + FirstName)
    private String gender;
    private LocalDate birth;         // 생년월일 or 나이
    private String foreigner;     // 내/외국인 여부
    private String mobilePhone;
    private String guardianTel;
    private LocalDateTime lastVisitDate;
    private LocalDateTime createDate;
}
