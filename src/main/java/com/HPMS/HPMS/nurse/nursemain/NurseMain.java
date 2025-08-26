package com.HPMS.HPMS.nurse.nursemain;

import com.HPMS.HPMS.nurse.nurseinformation.NurseInformation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class NurseMain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 10)
    private String dept;

    @Column(nullable = false, length = 4)
    private String rank;

    @Column(nullable = false, length = 50)
    private String fName;

    @Column(length = 50)
    private String lName;

    @Column(length = 50)
    private String mName;

    @Column(nullable = false, length = 1)
    private String gender;

    @Column(nullable = false)
    private Integer dateOfBirth;

    @Column(nullable = false)
    private Integer hireDate;

    @Column(nullable = false, length = 3)
    private String sts;

    @Column(nullable = false, length = 4)
    private String wt;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private Integer createDate;

    @Column(nullable = false)
    private String modifier;

    @Column(nullable = false)
    private Integer modifyDate;

    @Column(columnDefinition = "TEXT")
    private String note;

    @OneToOne(mappedBy = "NurseMain", cascade = CascadeType.REMOVE)
    private List<NurseInformation> nurseInformationList;
}
