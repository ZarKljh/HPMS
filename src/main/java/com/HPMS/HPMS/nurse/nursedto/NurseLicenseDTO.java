package com.HPMS.HPMS.nurse.nursedto;

import com.HPMS.HPMS.nurse.license.License;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NurseLicenseDTO {
    private Integer id;
    private String li; // 자격증명
    private String licenseNo;
    private String issueDate;
    private String expiryDate;
    private String note;

    public NurseLicenseDTO(License license) {
        this.id = license.getId();
        this.li = license.getLi();
        this.licenseNo = license.getLicenseNo();
        this.issueDate = license.getIssueDate();
        this.expiryDate = license.getExpiryDate();
        this.note = license.getNote();
    }
}
