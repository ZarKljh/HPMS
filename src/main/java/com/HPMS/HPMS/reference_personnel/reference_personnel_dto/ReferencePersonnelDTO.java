package com.HPMS.HPMS.reference_personnel.reference_personnel_dto;

import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelM;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReferencePersonnelDTO {
    /* ReferencePersonalM, ReferencePersonalDtl 엔터티에 동시 저장 */
    // ReferencePersonalM 관련 필드
    private Integer id; // update 과정에 필요
    private String firstName;
    private String lastName;
    private String middleName;
    private String nationality;
    private String email;
    private String cellPhone;
    private String creator;

    // ReferencePersonalDtl 관련 필드
    // private ReferencePersonnelM personnel; ViewModel이나 상세 조회용
    private Integer personnel;
    private String companyName;
    private String deptName;
    private String position;
    private String officeAddress;
    private String officeDetailAddress;
    private String officeTel;
    private String officeFax;
    private String companyWebsiteUrl;
    private String note;

    // 공통 사용 필드
    private LocalDateTime createDate;

    // FK: ReferencePersonalM의 ID
    private Long referencePersonalMId;
    // 동시에 다른 데이터를 같이 보여주기 위해 사용 private ReferencePersonnelM personnel;

    public String getCreator(){
        return creator;
    }

    public String getNationality() {
        return nationality;
    }

    public String getMiddleName() {
        return middleName;
    }

}
