package com.HPMS.HPMS.nurse.nursedto;

import com.HPMS.HPMS.nurse.nurseinformation.NurseInformation;
import lombok.Getter;

@Getter
public class NurseInformationDTO {
    private String firstName;
    private String lastName;
    private String middleName;
    private String tel;
    private String emgcCntc;
    private String emgcFName;
    private String emgcLName;
    private String emgcMName;
    private String emgcRel;
    private String emgcNote;
    private String email;
    private Integer pcd;
    private String defAdd;
    private String detAdd;
    private String rnNo;
    private String edbc;
    private Integer gradDate;
    private String fl;
    private String ms;
    private String natn;
    private String dss;
    private String carr;
    private String picture;
    private String note;

    public NurseInformationDTO(String firstName, String lastName, String middleName, String tel,
                               String emgcCntc, String emgcFName, String emgcLName, String emgcMName,
                               String emgcRel, String emgcNote, String email, Integer pcd,
                               String defAdd, String detAdd, String rnNo, String edbc,
                               Integer gradDate, String fl, String ms, String natn,
                               String dss, String carr, String picture, String note) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.tel = tel;
        this.emgcCntc = emgcCntc;
        this.emgcFName = emgcFName;
        this.emgcLName = emgcLName;
        this.emgcMName = emgcMName;
        this.emgcRel = emgcRel;
        this.emgcNote = emgcNote;
        this.email = email;
        this.pcd = pcd;
        this.defAdd = defAdd;
        this.detAdd = detAdd;
        this.rnNo = rnNo;
        this.edbc = edbc;
        this.gradDate = gradDate;
        this.fl = fl;
        this.ms = ms;
        this.natn = natn;
        this.dss = dss;
        this.carr = carr;
        this.picture = picture;
        this.note = note;
    }

    public NurseInformationDTO(NurseInformation info) {
        if (info != null) {
            this.firstName = info.getFirstName();
            this.middleName = info.getMiddleName();
            this.lastName = info.getLastName();
            this.tel = info.getTel();
            this.emgcCntc = info.getEmgcCntc();
            this.emgcFName = info.getEmgcFName();
            this.emgcLName = info.getEmgcLName();
            this.emgcMName = info.getEmgcMName();
            this.emgcRel = info.getEmgcRel();
            this.emgcNote = info.getEmgcNote();
            this.email = info.getEmail();
            this.pcd = info.getPcd();
            this.defAdd = info.getDefAdd();
            this.detAdd = info.getDetAdd();
            this.rnNo = info.getRnNo();
            this.edbc = info.getEdbc();
            this.gradDate = info.getGradDate();
            this.fl = info.getFl();
            this.ms = info.getMs();
            this.natn = info.getNatn();
            this.dss = info.getDss();
            this.carr = info.getCarr();
            this.picture = info.getPicture();
            this.note = info.getNote();
        }
    }
}