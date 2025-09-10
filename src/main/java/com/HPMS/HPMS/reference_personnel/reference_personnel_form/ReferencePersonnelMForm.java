package com.HPMS.HPMS.reference_personnel.reference_personnel_form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReferencePersonnelMForm {
    // ReferencePersonnelM 엔터트 전용 Form
    // ReferencePersonnelM 관련 필드
    private Integer id;
    // @NotBlank(message = "국적은 필수 항목입니다.")

    private String nationality = "KR"; // 국적 Default 설정

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String firstName;

    @NotBlank(message = "성은 필수 항목입니다.")
    private String lastName;

    private String middleName;

    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @Pattern(regexp = "\\d{10,11}", message = "휴대폰 번호는 숫자 10~11자리여야 합니다.")
    private String cellPhone;

    private String creator;

    private LocalDateTime createDate;
}
