package com.HPMS.HPMS.nurse.license;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "LICENSE")
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "NURSE_ID", nullable = false)
    private NurseMain nurseId; //간호사 번호

    @Column(length = 250, name = "LI")
    private String li; //자격증 이름

    @Column(length = 20, name = "LICENSE_NO")
    private String licenseNo; //자격증 번호

    @Column(length = 8, name = "ISSUE_DATE")
    private String issueDate; //발급날짜

    @Column(length = 8, name = "EXPIRY_DATE")
    private String expiryDate; //유효날짜

    @Column(length = 250, name = "NOTE")
    private String note; //비고
}
