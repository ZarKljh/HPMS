package com.HPMS.HPMS.Doctor.DoctorM;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
public class DoctorMForm {

    @NotBlank @Size(max = 4)
    private String department;

    @Size(max = 50)
    private String medicalDepartment;

    @NotBlank @Size(max = 4)
    private String rank;

    @Size(max = 4)
    private String position;

    @NotBlank @Size(max = 50)
    private String firstName;

    @NotBlank @Size(max = 50)
    private String lastName;

    @Size(max = 50)
    private String middleName;

    @NotBlank @Size(max = 1)
    @Pattern(regexp = "M|F", message = "성별은 M 또는 F 여야 합니다.")
    private String gender;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // yyyy-MM-dd
    private LocalDate dateOfBirth;

    @NotBlank @Size(max = 20)
    private String telephone;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // yyyy-MM-dd
    private LocalDate hireDate;

    @NotBlank @Size(max = 3)
    private String status;

    @NotBlank @Size(max = 4)
    private String workType;

    public static DoctorMForm fromEntity(DoctorM m) {
        DoctorMForm f = new DoctorMForm();
        f.setDepartment(m.getDepartment());
        f.setMedicalDepartment(m.getMedicalDepartment());
        f.setRank(m.getRank());
        f.setPosition(m.getPosition());
        f.setFirstName(m.getFirstName());
        f.setLastName(m.getLastName());
        f.setMiddleName(m.getMiddleName());
        f.setGender(m.getGender());
        f.setDateOfBirth(m.getDateOfBirth());
        f.setTelephone(m.getTelephone());
        f.setHireDate(m.getHireDate());
        f.setStatus(m.getStatus());
        f.setWorkType(m.getWorkType());
        return f;
    }
}
