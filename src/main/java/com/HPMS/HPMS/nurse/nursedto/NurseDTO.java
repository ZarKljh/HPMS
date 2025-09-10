package com.HPMS.HPMS.nurse.nursedto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NurseDTO {
    private NurseMainDTO nurseMain;
    private NurseInformationDTO nurseInformation;
    private List<NurseLicenseDTO> licenseList;

    public NurseDTO() {
        this.nurseMain = new NurseMainDTO();
        this.nurseInformation = new NurseInformationDTO();
    }

    public NurseDTO(NurseMainDTO nurseMain, NurseInformationDTO nurseInformation) {
        this.nurseMain = nurseMain;
        this.nurseInformation = nurseInformation;
    }
}