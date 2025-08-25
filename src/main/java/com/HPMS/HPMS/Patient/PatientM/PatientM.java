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
public class PatientM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(length = 50)
    private String fName;
    @NotNull
    @Column(length = 50)
    private String lName;
    @Column(length = 50)
    private String mName;
    @Column(length = 50)
    private String passFName;
    @Column(length = 50)
    private String passLName;
    @Column(length = 50)
    private String passMName;

    @NotNull
    @Column(length = 50)
    private String gender;
    @NotNull
    private Integer dayOfBirth;
    @NotNull
    private Integer foreigner;
    @Column(length = 20)
    private String passport;
    @NotNull
    private LocalDateTime createDate;

    private Integer delStaus;

    @OneToOne(mappedBy = "patientM", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PatientDTL patientDTL;
}
