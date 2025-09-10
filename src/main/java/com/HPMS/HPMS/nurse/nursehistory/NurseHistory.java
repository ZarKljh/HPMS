package com.HPMS.HPMS.nurse.history;

import com.HPMS.HPMS.nurse.license.License;
import com.HPMS.HPMS.nurse.nursedto.NurseInformationDTO;
import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "NURSE_HISTORY")
public class NurseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

//    기본정보

    @NotBlank
    @Size(max = 50)
    @Column(name = "FIRST_NAME", length = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    @Column(name = "LAST_NAME", length = 50)
    private String lastName;

    @Size(max = 50)
    @Column(name = "MIDDLE_NAME", length = 50)
    private String middleName;

    @Size(max = 4)
    @Column(name = "DEPT", length = 4)
    private String dept;

    @Size(max = 4)
    @Column(name = "RANK", length = 4)
    private String rank;

    @Size(max = 1)
    @Column(name = "GENDER", length = 1)
    private String gender;

    @NotNull
    @Column(name = "DATE_OF_BIRTH")
    private String dateOfBirth;

    @NotNull
    @Column(name = "HIRE_DATE")
    private String hireDate;

    @Size(max = 3)
    @Column(name = "STS", length = 3)
    private String status;

    @Size(max = 4)
    @Column(name = "WT", length = 4)
    private String workType;

//    상세정보

    @Size(max = 250)
    @Column(name = "IMG", length = 250)
    private String picture;

    @Size(max = 20)
    @Column(name = "TEL", length = 20)
    private String tel;

    @Size(max = 20)
    @Column(name = "EMGC_CNTC", length = 20)
    private String emergencyContact;

    @Size(max = 50)
    @Column(name = "EMGC_F_NAME", length = 50)
    private String emergencyFirstName;

    @Size(max = 50)
    @Column(name = "EMGC_L_NAME", length = 50)
    private String emergencyLastName;

    @Size(max = 50)
    @Column(name = "EMGC_M_NAME", length = 50)
    private String emergencyMiddleName;

    @Size(max = 10)
    @Column(name = "EMGC_REL", length = 10)
    private String emergencyRelation;

    @Size(max = 250)
    @Column(name = "EMGC_NOTE", length = 250)
    private String emergencyNote;

    @Size(max = 100)
    @Column(name = "EMAIL", length = 100)
    private String email;

    @Size(max = 10)
    @Column(name = "RN_NO", length = 10)
    private String nurseLicenseNo;

    @Size(max = 100)
    @Column(name = "EDBC", length = 100)
    private String educationalBackground;

    @Column(name = "GRAD_DATE")
    private String gradDate;

    @Size(max = 250)
    @Column(name = "FL", length = 250)
    private String foreignLanguage;

    @Size(max = 10)
    @Column(name = "MS", length = 10)
    private String militaryService;

    @Size(max = 50)
    @Column(name = "NATN", length = 50)
    private String nationality;

    @Size(max = 1)
    @Column(name = "DSS", length = 1)
    private String disabilityStatus;

    @Size(max = 250)
    @Column(name = "CARR", length = 250)
    private String career;

    @Size(max = 255)
    @Column(name = "NOTE", length = 255)
    private String note;

//     라이센스 (스냅샷)

    @Size(max = 100)
    @Column(name = "LICENSE_NAME", length = 100)
    private String licenseName;

    @Size(max = 50)
    @Column(name = "LICENSE_NO", length = 50)
    private String licenseNo;

    @Column(name = "ISSUE_DATE")
    private String issueDate;

    @Column(name = "EXPIRY_DATE")
    private String expiryDate;

    @Size(max = 255)
    @Column(name = "LICENSE_NOTE", length = 255)
    private String licenseNote;

//    공통 정보

    @Column(name = "CREATE_DATE", nullable = false)
    private LocalDateTime createDate;

    @Size(max = 250)
    @Column(name = "WRITER", nullable = false, length = 250)
    private String writer;

    @Size(max = 250)
    @Column(name = "MODIFIER", length = 250)
    private String modifier;

    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NURSE_ID", nullable = false)
    private NurseMain nurseMain;

    @PrePersist
    void onPrePersist() {
        if (createDate == null) createDate = LocalDateTime.now();
        if (writer == null || writer.isBlank()) writer = "system";
    }

    /** NurseMain + NurseInformation + License → Snapshot */
    public static NurseHistory snapshotOf(NurseMain m, NurseInformationDTO i, License l) {
        return NurseHistory.builder()
                .nurseMain(m)
                .createDate(LocalDateTime.now())
                .writer("system")

                // main
                .firstName(m.getFirstName())
                .lastName(m.getLastName())
                .middleName(m.getMiddleName())
                .dept(m.getDept())
                .rank(m.getRank())
                .gender(m.getGender())
                .dateOfBirth(String.valueOf(m.getDateOfBirth()))
                .hireDate(String.valueOf(m.getHireDate()))
                .status(m.getSts())
                .workType(m.getWt())

                // info
                .picture(i != null ? i.getPicture() : null)
                .tel(i != null ? i.getTel() : null)
                .emergencyContact(i != null ? i.getEmgcCntc() : null)
                .emergencyFirstName(i != null ? i.getEmgcFName() : null)
                .emergencyLastName(i != null ? i.getEmgcLName() : null)
                .emergencyMiddleName(i != null ? i.getEmgcMName() : null)
                .emergencyRelation(i != null ? i.getEmgcRel() : null)
                .emergencyNote(i != null ? i.getEmgcNote() : null)
                .email(i != null ? i.getEmail() : null)
                .nurseLicenseNo(i != null ? i.getRnNo() : null)
                .educationalBackground(i != null ? i.getEdbc() : null)
                .gradDate(String.valueOf(i != null ? i.getGradDate() : null))
                .foreignLanguage(i != null ? i.getFl() : null)
                .militaryService(i != null ? i.getMs() : null)
                .nationality(i != null ? i.getNatn() : null)
                .disabilityStatus(i != null ? i.getDss() : null)
                .career(i != null ? i.getCarr() : null)
                .note(i != null ? i.getNote() : null)

                // license
                .licenseName(l != null ? l.getLi() : null)
                .licenseNo(l != null ? l.getLicenseNo() : null)
                .issueDate(l != null ? l.getIssueDate() : null)
                .expiryDate(l != null ? l.getExpiryDate() : null)
                .licenseNote(l != null ? l.getNote() : null)

                .build();
    }
}