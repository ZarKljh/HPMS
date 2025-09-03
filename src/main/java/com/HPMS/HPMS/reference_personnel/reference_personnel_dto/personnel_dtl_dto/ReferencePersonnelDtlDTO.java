package com.HPMS.HPMS.reference_personnel.reference_personnel_dto.personnel_dtl_dto;


import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelM;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReferencePersonnelDtlDTO {
    private Integer id;
    private ReferencePersonnelM personnel;
    private String companyName;
    private String deptName;
    private String position;
    private String officeAddress;
    private String officeDetailAddress;
    private String officeTel;
    private String officeFax;
    private String companyWebsiteUrl;
    private String note;
    private String creator;
    private LocalDateTime createDate;

/*    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ReferencePersonnelM getPersonnel() {
        return personnel;
    }

    public void setPersonnel(ReferencePersonnelM personnel) {
        this.personnel = personnel;
    }
*/
    public String getCompanyName() {
        return companyName != null && !companyName.trim().isEmpty() ? companyName : "관련정보없음";
    }

    public String getDeptName() {
        // return deptName;
        return deptName != null && !deptName.trim().isEmpty() ? deptName : "관련정보없음";
    }

/*    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }*/

    public String getPosition() {
        //return position;
        return position != null && !position.trim().isEmpty() ? position : "관련정보없음";
    }

/*    public void setPosition(String position) {
        this.position = position;
    }*/

    public String getOfficeAddress() {
        // return officeAddress;
        return officeAddress != null && !officeAddress.trim().isEmpty() ? officeAddress : "관련정보없음";
    }

/*    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }*/

    public String getOfficeDetailAddress() {
        //return officeDetailAddress;
        return officeDetailAddress != null && !officeDetailAddress.trim().isEmpty() ? officeDetailAddress : "관련정보없음";
    }

/*    public void setOfficeDetailAddress(String officeDetailAddress) {
        this.officeDetailAddress = officeDetailAddress;
    }*/

    public String getOfficeTel() {
        // return officeTel;
        return officeTel != null && !officeTel.trim().isEmpty() ? officeTel : "관련정보없음";
    }

/*    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }*/

    public String getOfficeFax() {
        // return officeFax;
        return officeFax != null && !officeFax.trim().isEmpty() ? officeFax : "관련정보없음";
    }

/*    public void setOfficeFax(String officeFax) {
        this.officeFax = officeFax;
    }*/

    public String getCompanyWebsiteUrl() {
        //return companyWebsiteUrl;
        return companyWebsiteUrl != null && !companyWebsiteUrl.trim().isEmpty() ? companyWebsiteUrl : "관련정보없음";
    }

/*    public void setCompanyWebsiteUrl(String companyWebsiteUrl) {
        this.companyWebsiteUrl = companyWebsiteUrl;
    }*/

    public String getNote() {
        // return note;
        return note != null && !note.trim().isEmpty() ? note : "관련정보없음";
    }

/*    public void setNote(String note) {
        this.note = note;
    }*/

/*
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
*/

}
