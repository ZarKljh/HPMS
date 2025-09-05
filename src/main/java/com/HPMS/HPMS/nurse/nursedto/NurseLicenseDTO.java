package com.HPMS.HPMS.nurse.nursedto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NurseLicenseDTO {
    private Integer id;
    private String li; // 자격증명
    private String licenseNo;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String note;
}
