package com.HPMS.HPMS.nurse.NurseDTO;

import lombok.Getter;

@Getter
public class NurseDTO {
    private NurseMainDTO nurseMain;
    private NurseInformationDTO nurseInformation;

    public NurseDTO(NurseMainDTO nurseMain, NurseInformationDTO nurseInformation) {
        this.nurseMain = nurseMain;
        this.nurseInformation = nurseInformation;
    }
}