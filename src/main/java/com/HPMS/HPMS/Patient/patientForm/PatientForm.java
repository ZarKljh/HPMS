package com.HPMS.HPMS.Patient.patientForm;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PatientForm {
    private Integer id;
    private String delStatus; // 1 = 종결
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private LocalDate birth;
    @NotEmpty(message="성별을 입력해주세요")// 생년월일 or 나이
    private String gender;
    @NotEmpty(message="내외국인을 구분해주세요")
    private String foreigner;
    @NotEmpty(message="성명 성을 입력해주세요")
    @Size(max=20)
    private String firstName;
    @NotEmpty(message="성명 이름을 입력해주세요")
    @Size(max=20)
    private String lastName;
    private String middleName; // 성명 (LastName + FirstName)
    private String passport;
    private String passFirstName; // 여권성명 (PassLastName + PassFirstName)
    private String passLastName;
    private String passMiddleName;

    @NotEmpty(message="휴대전화번호는 필수항목입니다")
    @Size(max=20)
    private String mobilePhone;
    private String homePhone;
    private String officePhone;
    private String email;
    private String fax;

    @NotEmpty(message="보호자정보는 필수사항입니다")
    @Size(max=50)
    private String guardianFirstName;
    @NotEmpty(message="보호자정보는 필수사항입니다")
    private String guardianLastName;
    private String guardianMiddleName;
    @NotEmpty(message="보호자정보는 필수사항입니다")
    @Size(max=50)
    private String guardianTel;
    @NotEmpty(message="보호자정보는 필수사항입니다")
    @Size(max=50)
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
