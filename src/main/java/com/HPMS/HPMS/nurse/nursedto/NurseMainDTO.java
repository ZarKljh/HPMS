package com.HPMS.HPMS.nurse.nursedto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NurseMainDTO {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dept;
    private String rank;
    private String gender;
    private String dateOfBirth; // 19890315 -> "1989-03-15"
    private String hireDate;    // 20190101 -> "2019-01-01"
    private String sts;         // 상태 (재직중 / 휴직 / 퇴직 등)
    private String wt;          // 근무형태 (정규직 / 계약직 / 파트타임 등)
    private String writer;      // 작성자
    private LocalDateTime createDate;
    private String modifier;    // 수정자
    private LocalDateTime modifyDate;

    // 모든 필드를 포함한 생성자
    public NurseMainDTO(Integer id, String firstName, String middleName, String lastName, String dept,
                        String rank, String gender, String dateOfBirth, String hireDate,
                        String sts, String wt, String writer, LocalDateTime createDate,
                        String modifier, LocalDateTime modifyDate) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dept = dept;
        this.rank = rank;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.hireDate = hireDate;
        this.sts = sts;
        this.wt = wt;
        this.writer = writer;
        this.createDate = createDate;
        this.modifier = modifier;
        this.modifyDate = modifyDate;
    }

    // 기본 생성자 (Thymeleaf, Jackson 등에서 필요할 수 있음)
    public NurseMainDTO() {}
}