package com.HPMS.HPMS.Patient.PatientDTL;

import com.HPMS.HPMS.Patient.PatientM.PatientM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="TBL_PATIENT_DTL")
public class PatientDTL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    //FK
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PA_ID", nullable = false)
    private PatientM patientM;

    @Column(name = "MO_PHONE_NUM", nullable = false, length = 20)
    private String mobilePhone;

    @Column(name = "HOME_PHONE_NUM", length = 20)
    private String homePhone;

    @Column(name = "OFFICE_PHONE_NUM", length = 20)
    private String officePhone;

    @Column(name = "EMAIL", length = 50)
    private String email;

    @Column(name = "FAX", length = 20)
    private String fax;

    @Column(name = "GURAD_TEL", nullable = false, length = 20)
    private String guardianTel;

    @Column(name = "GURAD_RELA", nullable = false, length = 20)
    private String guardianRelation;

    @Column(name = "GURAD_FIRST_NAME", nullable = false, length = 50)
    private String guardianFirstName;

    @Column(name = "GURAD_LAST_NAME", nullable = false, length = 50)
    private String guardianLastName;

    @Column(name = "GURAD_MIDDLE_NAME", length = 50)
    private String guardianMiddleName;

    @Column(name = "CUR_HOME_PCD", length = 10)
    private String curHomePCD;

    @Column(name = "CUR_HOME_DEF_ADD", length = 255)
    private String curHomeDefAdd;

    @Column(name = "CUR_HOME_DET_ADD", length = 255)
    private String curHomeDetAdd;

    @Column(name = "REG_HOME_PCD", length = 10)
    private String regHomePCD;

    @Column(name = "REG_HOME_DEF_ADD", length = 255)
    private String regHomeDefAdd;

    @Column(name = "REG_HOME_DET_ADD", length = 255)
    private String regHomeDetAdd;

    @Column(name = "OCCUPATION", length = 50)
    private String occupation;

    @Column(name = "LASTVISIT_DATE", nullable = false,
            columnDefinition = "DATETIME DEFAULT NOW()")
    private java.time.LocalDateTime lastVisitDate;

    @Column(name = "NATN", length = 50)
    private String natn;

    @Column(name = "NOTE", columnDefinition = "TEXT")
    private String note;
}