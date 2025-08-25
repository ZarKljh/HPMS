package com.HPMS.HPMS.PatientDTL;

import com.HPMS.HPMS.PatientM.PatientM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PatientDTL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private PatientM patientM;

    @Column(length = 20)
    private String moPhoneNumber;
    @Column(length = 20)
    private String homePhoneNumber;
    @Column(length = 20)
    private String officePhoneNumber;
    @Column(length = 50)
    private String email;
    @Column(length = 20)
    private String fax;

    @Column(length = 50)
    private String guardTel;
    @Column(length = 50)
    private String guardRela;
    @Column(length = 50)
    private String guardFName;
    @Column(length = 50)
    private String guardLName;
    @Column(length = 50)
    private String guardMName;

    @Column(length = 10)
    private String curHomePcd;
    @Column(length = 255)
    private String curHomeDefAdd;
    @Column(length = 255)
    private String curHomeDetAdd;

    @Column(length = 10)
    private String regHomePcd;
    @Column(length = 255)
    private String regHomeDefAdd;
    @Column(length = 255)
    private String regHomeDetAdd;

    @Column(length = 50)
    private String occupation;

    private LocalDateTime lastvistDate;

    @Column(length = 50)
    private String natn;

    @Column(columnDefinition = "TEXT")
    private String note;
}
