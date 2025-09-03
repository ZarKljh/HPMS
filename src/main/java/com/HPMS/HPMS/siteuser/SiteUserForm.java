package com.HPMS.HPMS.siteuser;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SiteUserForm {

    @NotEmpty(message="ID를 입력해주세요")
    @Size(max=20)
    private String userId; // 로그인 ID
    @NotEmpty(message="패스워드를 입력해주세요")
    @Size(max=20)
    private String password1; // 로그인 비밀번호(암호화 저장)
    @NotEmpty(message="성명 성을 입력해주세요")
    @Size(max=50)
    private String password2; // 로그인 비밀번호(암호화 저장)
    @NotEmpty(message="성명 성을 입력해주세요")
    @Size(max=50)

    private String lastName; // 사용자 성명 성
    @NotEmpty(message="성명 이름을 입력해주세요")
    @Size(max=50)
    private String firstName; // 사용자 성명 이름


    private String middleName; // 사용자 성명 중간이름

    @NotEmpty(message="휴대전화번호를 입력해주세요")
    @Size(max=50)
    private String mobilePhone; // 사용자 휴대전화

    private String email; // 사용자 이메일

    private String role; // 직책: N=간호사, D=의사, R=지원부서, S=시스템관리자

    private Integer roleId; // 직책별 직원정보 테이블 ID

    private String status = "ACTIVE"; // 계정 상태

    private LocalDateTime createDate; // 계정 생성일

}
