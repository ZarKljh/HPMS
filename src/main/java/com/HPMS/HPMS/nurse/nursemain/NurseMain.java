package com.HPMS.HPMS.nurse.nursemain;

import com.HPMS.HPMS.nurse.nurseinformation.NurseInformation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "NURSE_MAIN")
public class NurseMain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(nullable = false, length = 10, name = "DEPT")
    private String dept; //부서

    @Column(nullable = false, length = 4, name = "RANK")
    private String rank; //직급

    @Column(nullable = false, length = 50, name = "FIRST_NAME")
    private String firstName; //이름

    @Column(length = 50, name = "LAST_NAME")
    private String lastName; //성

    @Column(length = 50, name = "MIDDLE_NAME")
    private String middleName; //중간이름

    @Column(nullable = false, length = 1, name = "GENDER")
    private String gender; //성별 (F / M)

    @Column(nullable = false, name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth; //생년월일 8자리

    @Column(nullable = false, name = "HIREDATE")
    private LocalDate hireDate; //입사일

    @Column(nullable = false, length = 3, name = "STS")
    private String sts; //상태 (재직중 / 휴직 / 퇴직 등)

    @Column(nullable = false, length = 4, name = "WT")
    private String wt; //근무형태 (정규직 / 계약직 / 파트타임 등)

    @Column(nullable = false, name = "WRITER")
    private String writer; //작성자

    @Column(nullable = false, name = "CREATE_DATE")
    private LocalDateTime createDate; //작성시간

    @Column(nullable = false, name = "MODIFIER")
    private String modifier; //수정자

    @Column(nullable = false, name = "MODIFY_DATE")
    private LocalDateTime modifyDate; //수정시간

    @OneToOne(mappedBy = "nurseMain", cascade = CascadeType.ALL)
    private NurseInformation nurseInformation;
}
