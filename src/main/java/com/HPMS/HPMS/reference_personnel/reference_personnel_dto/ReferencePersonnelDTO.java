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
    // 공통사용
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

    // personnel 전체 갯수와 personnel 개별 순번 관리
    private int currentNum; // 전체 순번
    private int totalNum;   // 전체 개수



    public String getFirstName(){ return firstName; }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName(){ return lastName; }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName(){ return middleName; }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPosition(){
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }


    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }

}
