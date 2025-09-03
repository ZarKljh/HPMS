package com.HPMS.HPMS.Patient.PatientH;

import com.HPMS.HPMS.Patient.PatientDTL.PatientDTL;
import com.HPMS.HPMS.Patient.PatientM.PatientM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="TBL_PATIENT_H")
public class PatientH {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PA_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_PATIENT_H"))
    private PatientM patientM;  // TBL_PATIENT_M 엔티티

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
    private Integer dayOfBirth;

    @Column(name = "FOREIGNER", nullable = false)
    private Integer foreigner = 0;

    @Column(name = "PASSPORT", length = 10)
    private String passport;

    @Column(name = "CREATEDATE", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "UPDATEDATE", nullable = false)
    private LocalDateTime updateDate;

    @Column(name = "DEL_STATUS")
    private Integer delStatus;

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
    private String guradianTel;

    @Column(name = "GURAD_RELA", nullable = false, length = 20)
    private String guradianRelation;

    @Column(name = "GURAD_FIRST_NAME", nullable = false, length = 50)
    private String guradianFirstName;

    @Column(name = "GURAD_LAST_NAME", nullable = false, length = 50)
    private String guradianLastName;

    @Column(name = "GURAD_MIDDLE_NAME", length = 50)
    private String guradianMiddleName;

    @Column(name = "CUR_HOME_PCD", length = 10)
    private String curHomePcd;

    @Column(name = "CUR_HOME_DEF_ADD", length = 255)
    private String curHomeDefAdd;

    @Column(name = "CUR_HOME_DET_ADD", length = 255)
    private String curHomeDetAdd;

    @Column(name = "REG_HOME_PCD", length = 10)
    private String regHomePcd;

    @Column(name = "REG_HOME_DEF_ADD", length = 255)
    private String regHomeDefAdd;

    @Column(name = "REG_HOME_DET_ADD", length = 255)
    private String regHomeDetAdd;

    @Column(name = "OCCUPATION", length = 50)
    private String occupation;

    @Column(name = "LASTVISIT_DATE", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private LocalDateTime lastVisitDate;

    @Column(name = "NATN", length = 50)
    private String natn;

    @Column(name = "NOTE", columnDefinition = "TEXT")
    private String note;
}
