package com.HPMS.HPMS.reference_personnel.reference_personnel_form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ReferencePersonnelDTOForm {
    // ReferencePersonnelM, ReferencePersonnelDtl 통합
    // ReferencePersonnelM 관련 필드
    private Integer id; // 수정 시 사용

    @NotBlank(message = "이름을 입력해주세요.")
    private String firstName;

    @NotBlank(message = "성을 입력해주세요.")
    private String lastName;

    private String middleName;

    // @NotBlank(message = "국적을 입력해주세요.")
    private String nationality = "KR";

    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @Pattern(regexp = "\\d{10,11}", message = "휴대폰 번호는 숫자 10~11자리여야 합니다.")
    private String cellPhone;

    private String creator;

    private LocalDateTime createDate;

    // ReferencePersonnelDtl 관련 필드
    private Integer personnel; // 연관된 M의 ID

    // @NotBlank(message = "회사명을 입력해주세요.")
    private String companyName;

    private String deptName;

    private String position;

    private String officeAddress;

    private String officeDetailAddress;

    @Pattern(regexp = "\\d{10,11}", message = "사무실 전화번호는 숫자 10~11자리여야 합니다.")
    private String officeTel;

    private String officeFax;

    @Pattern(regexp = "^(https?://)?[\\w.-]+(?:\\.[\\w\\.-]+)+[/#?]?.*$", message = "유효한 웹사이트 주소를 입력해주세요.")
    private String companyWebsiteUrl;

    private String note;

    // FK: ReferencePersonnelM의 ID
    private Long referencePersonalMId;

    // 순번 관리
    private int currentNum;
    private int totalNum;
}
