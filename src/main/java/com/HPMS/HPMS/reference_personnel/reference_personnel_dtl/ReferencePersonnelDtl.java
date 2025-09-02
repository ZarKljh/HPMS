package com.HPMS.HPMS.reference_personnel.reference_personnel_dtl;

import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "TBL_REL_PERSONNEL_DTL")
public class ReferencePersonnelDtl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "TBL_ETC_MEM_ID") // FK 컬럼
    private ReferencePersonnelM personnel;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "DEPT_NAME")
    private String deptName;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "OFFICE_ADDRESS")
    private String officeAddress;

    @Column(name = "OFFICE_DETAIL_ADDRESS")
    private String officeDetailAddress;

    @Column(name = "OFFICE_TEL")
    private String officeTel;

    @Column(name = "OFFICE_FAX")
    private String officeFax;

    @Column(name = "COMPANY_WEBSITE_URL")
    private String companyWebsiteUrl;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "CREATOR")
    private String creator;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    public String getCompanyName() {
        return companyName != null ? companyName.toUpperCase() : "N/A";
    }
}

