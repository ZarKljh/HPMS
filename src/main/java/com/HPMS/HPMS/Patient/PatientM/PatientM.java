package com.HPMS.HPMS.Patient.PatientM;


import com.HPMS.HPMS.Patient.PatientDTL.PatientDTL;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="TBL_PATIENT_M")
public class PatientM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "ID")
    private Integer id;

    @Column(name = "FIRST_NAME", nullable = false, length = 50)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 50)
    private String lastName;

    @Column(name = "MIDDLE_NAME", length = 50)
    private String middleName;

    @Column(name = "PASS_FIRST_NAME", length = 50)
    private String passFirstName;

    @Column(name = "PASS_LAST_NAME", length = 50)
    private String passLastName;

    @Column(name = "PASS_MIDDLE_NAME", length = 50)
    private String passMiddleName;

    @Column(name = "GENDER", nullable = false, length = 1)
    private String gender;

    @Column(name = "DAY_OF_BIRTH", nullable = false)
    private Integer dayOfBirth;  // INT(8) → YYYYMMDD 형태

    @Column(name = "FOREIGNER", nullable = false, length=1)
    private String foreigner;   // 0=내국인, 1=외국인

    @Column(name = "PASSPORT", length = 20)
    private String passport;

    @Column(name = "CREATEDATE", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "UPDATEDATE", nullable = false)
    private LocalDateTime updateDate;

    @Column(name = "DEL_STATUS")
    private Integer delStatus;

    @OneToOne(mappedBy = "patientM", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PatientDTL patientDTL;
}
