package com.HPMS.HPMS.nurse.nursemain;

import com.HPMS.HPMS.nurse.nurseinformation.NurseInformation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    private String dept;

    @Column(nullable = false, length = 4, name = "RANK")
    private String rank;

    @Column(nullable = false, length = 50, name = "FIRST_NAME")
    private String firstName;

    @Column(length = 50, name = "LAST_NAME")
    private String lastName;

    @Column(length = 50, name = "MIDDLE_NAME")
    private String middleName;

    @Column(nullable = false, length = 1, name = "GENDER")
    private String gender;

    @Column(nullable = false, name = "DATE_OF_BIRTH")
    private Integer dateOfBirth;

    @Column(nullable = false, name = "HIREDATE")
    private Integer hireDate;

    @Column(nullable = false, length = 3, name = "STS")
    private String sts;

    @Column(nullable = false, length = 4, name = "WT")
    private String wt;

    @Column(nullable = false, name = "WRITER")
    private String writer;

    @Column(nullable = false, name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(nullable = false, name = "MODIFIER")
    private String modifier;

    @Column(nullable = false, name = "MODIFY_DATE")
    private LocalDateTime modifyDate;

    @OneToOne(mappedBy = "NurseMain", cascade = CascadeType.REMOVE)
    private List<NurseInformation> nurseInformationList;
}
