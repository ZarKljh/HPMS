package com.HPMS.HPMS.siteuser;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TBL_SITE_USER")
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Integer id; // 테이블 고유값, 자동 증가

    @Column(name = "USER_ID", nullable = false, unique = true, length = 20)
    private String userId; // 로그인 ID

    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password; // 로그인 비밀번호(암호화 저장)

    @Column(name = "LAST_NAME", nullable = false, length = 50)
    private String lastName; // 사용자 성명 성

    @Column(name = "FIRST_NAME", nullable = false, length = 50)
    private String firstName; // 사용자 성명 이름

    @Column(name = "MIDDLE_NAME", length = 50)
    private String middleName; // 사용자 성명 중간이름

    @Column(name = "MOBILE_PHONE", nullable = false, length = 25)
    private String mobilePhone; // 사용자 휴대전화

    @Column(name = "EMAIL", length = 250)
    private String email; // 사용자 이메일

    @Column(name = "ROLE", length = 20)
    private String role; // 직책: N=간호사, D=의사, R=지원부서, S=시스템관리자

    @Column(name = "ROLE_ID")
    private Integer roleId; // 직책별 직원정보 테이블 ID

    @Column(name = "STATUS", length = 20)
    private String status; // 계정 상태

    @Column(name = "CREATEDATE", nullable = false, updatable = false)
    private LocalDateTime createDate; // 계정 생성일

}
