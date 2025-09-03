package com.HPMS.HPMS.reference_personnel.reference_personnel_dto.personnel_m_dto;
import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelM;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReferencePersonnelMDTO {
    private Integer id;
    private String nationality;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String cellPhone;
    private String creator;
    private LocalDateTime createDate;
}
