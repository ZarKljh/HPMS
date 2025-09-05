package com.HPMS.HPMS.nurse.nursedto;

import com.HPMS.HPMS.nurse.license.License;
import lombok.Getter;

import java.util.List;

@Getter
public class NurseDTO {
    private NurseMainDTO nurseMain;
    private NurseInformationDTO nurseInformation;
    private List<License> licenseList;

    public NurseDTO(NurseMainDTO nurseMain, NurseInformationDTO nurseInformation) {
        this.nurseMain = nurseMain;
        this.nurseInformation = nurseInformation;
    }
}