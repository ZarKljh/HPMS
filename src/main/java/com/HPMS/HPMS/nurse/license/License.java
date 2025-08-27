package com.HPMS.HPMS.nurse.license;

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

    @Column(nullable = false, name = "NURSE_ID")
    private Integer nurseId;

    @Column(length = 250, name = "LI")
    private String li;

    @Column(length = 20, name = "LICENSE_NO")
    private String licenseNo;

    @Column(length = 8, name = "ISSUE_DATE")
    private String issueDate;

    @Column(length = 8, name = "EXPIRY_DATE")
    private String expiryDate;

    @Column(length = 250, name = "NOTE")
    private String note;
}
