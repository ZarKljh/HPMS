package com.HPMS.HPMS.Doctor.DoctorDTL;

import com.HPMS.HPMS.Doctor.DoctorDTL.DoctorDTL;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DoctorDTLForm {

    /** 수정 시 사용 */
    private Integer doctorId;

    @NotBlank @Size(max = 250)
    private String image;

    @NotBlank @Size(max = 20)
    private String userId;

    @Size(max = 100)
    private String userPassword;

    @NotBlank @Size(max = 20)
    private String officeTelephone;

    @Size(max = 20)
    private String emergencyContact;

    @Size(max = 50)
    private String emergencyFirstName;

    @Size(max = 50)
    private String emergencyLastName;

    @Size(max = 50)
    private String emergencyMiddleName;

    @Size(max = 10)
    private String emergencyRelation;

    @Size(max = 250)
    private String emergencyNote;

    @Email @Size(max = 250)
    private String email;

    @Size(max = 6)
    private String postcode;

    @Size(max = 250)
    private String defaultAddress;

    @Size(max = 250)
    private String detailedAddress;

    @NotBlank @Size(max = 10)
    private String registrationNumber;

    @Size(max = 100)
    private String educationalBackground;

    @Size(max = 250)
    private String foreignLanguage;

    @Size(max = 250)
    private String license;

    @NotBlank @Size(max = 50)
    private String major;

    @Size(max = 250)
    private String workDependsOnExperience;

    @Size(max = 250)
    private String awards;

    @Size(max = 250)
    private String societyActivity;

    @Size(max = 250)
    private String certificateOfSociety;

    @Size(max = 10)
    private String militaryService;

    @Size(max = 50)
    private String nationality;

    @Size(max = 1)
    private String disabilityStatus;

    @Size(max = 255)
    private String note;

    public static DoctorDTLForm fromEntity(DoctorDTL d) {
        DoctorDTLForm f = new DoctorDTLForm();
        if (d.getDoctorMain()!=null) f.setDoctorId(d.getDoctorMain().getId());
        f.setImage(d.getImage());
        f.setUserId(d.getUserId());
        f.setUserPassword(d.getUserPassword());
        f.setOfficeTelephone(d.getOfficeTelephone());
        f.setEmergencyContact(d.getEmergencyContact());
        f.setEmergencyFirstName(d.getEmergencyFirstName());
        f.setEmergencyLastName(d.getEmergencyLastName());
        f.setEmergencyMiddleName(d.getEmergencyMiddleName());
        f.setEmergencyRelation(d.getEmergencyRelation());
        f.setEmergencyNote(d.getEmergencyNote());
        f.setEmail(d.getEmail());
        f.setPostcode(d.getPostcode());
        f.setDefaultAddress(d.getDefaultAddress());
        f.setDetailedAddress(d.getDetailedAddress());
        f.setRegistrationNumber(d.getRegistrationNumber());
        f.setEducationalBackground(d.getEducationalBackground());
        f.setForeignLanguage(d.getForeignLanguage());
        f.setLicense(d.getLicense());
        f.setMajor(d.getMajor());
        f.setWorkDependsOnExperience(d.getWorkDependsOnExperience());
        f.setAwards(d.getAwards());
        f.setSocietyActivity(d.getSocietyActivity());
        f.setCertificateOfSociety(d.getCertificateOfSociety());
        f.setMilitaryService(d.getMilitaryService());
        f.setNationality(d.getNationality());
        f.setDisabilityStatus(d.getDisabilityStatus());
        f.setNote(d.getNote());
        return f;
    }
}
