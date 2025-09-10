package com.HPMS.HPMS.reference_personnel.reference_personnel_form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReferencePersonnelDtlForm {
    private Integer id;

    // 회사 정보
    // @NotBlank(message = "회사명은 필수 항목입니다.")
    private String companyName;

    private String deptName;

    private String position;

    // 주소 정보
    private String officeAddress;

    private String officeDetailAddress;

    // 연락처
    // @Pattern(regexp = "\\d{2,4}-\\d{3,4}-\\d{4}", message = "전화번호 형식이 올바르지 않습니다.")
    @Pattern(regexp = "\\d{10,11}", message = "전화번호는 숫자 10~11자리여야 합니다.")
    private String officeTel;

    private String officeFax;

    @URL(message = "유효한 웹사이트 주소를 입력해주세요.")
    private String companyWebsiteUrl;

    // 기타
    private String note;

    private String creator;

    private LocalDateTime createDate;

    // 연관된 M 엔터티의 ID (선택적으로 포함)
    private Integer personnelId;
    // 엔터티에서는 private ReferencePersonnelM personnel 와 같이 선언되어 있음
}
