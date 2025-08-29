package com.HPMS.HPMS.nurse.nurseinformation;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/nurse")
@RequiredArgsConstructor
@Controller
public class NurseInformationController {

    private final NurseMainService nurseMainService;
    private final NurseInformationService nurseInformationService;

    @PostMapping("/create/{nurseId}")
    public String createNurseInformation(Model model, @PathVariable("id") Integer id, @RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName, @RequestParam(value="middleName") String middleName, @RequestParam(value="tel") String tel, @RequestParam(value="emgcCntc") String emgcCntc, @RequestParam(value="emgcFName") String emgcFName, @RequestParam(value="emgcLName") String emgcLName, @RequestParam(value="emgcMName") String emgcMName, @RequestParam(value="emgcRel") String emgcRel, @RequestParam(value="emgcNote") String emgcNote, @RequestParam(value="email") String email, @RequestParam(value="pcd") Integer pcd, @RequestParam(value="defAdd") String defAdd, @RequestParam(value="detAdd") String detAdd, @RequestParam(value="rnNo") String rnNo, @RequestParam(value="edbc") String edbc, @RequestParam(value="gradDate") Integer gradDate, @RequestParam(value="fl") String fl, @RequestParam(value="ms") String ms, @RequestParam(value="natn") String natn, @RequestParam(value="dss") String dss, @RequestParam(value="carr") String carr, @RequestParam(value="picture") String picture, @RequestParam(value="note") String note) {
        NurseMain nurseMain = this.nurseMainService.getNurseMain(id);
        NurseInformation nurseInformation = this.nurseInformationService.create(nurseMain, firstName, lastName, middleName, tel, emgcCntc, emgcFName, emgcLName, emgcMName, emgcRel, emgcNote, email, pcd, defAdd, detAdd, rnNo, edbc, gradDate, fl, ms, natn, dss, carr, picture, note);
        model.addAttribute("nurseMain", nurseMain);
        model.addAttribute("nurseInformation", nurseInformation);
        return String.format("redirect:/nurse/information/%s", id);
    }

    @PostMapping("/information/{nurseId}")
    public String createInformation(@PathVariable("nurseId") Integer nurseId, Model model) {
        NurseMain nurseMain = nurseMainService.getNurseMain(nurseId);
        NurseInformation nurseInformation = nurseInformationService.findByNurseMainId(nurseId);

        // 정보 없으면 빈 객체 생성
        if (nurseInformation == null) {
            nurseInformation = new NurseInformation();
            nurseInformation.setNurseMain(nurseMain);
        }

        // DTO에 엔티티 값 복사
        NurseInfoDTO dto = new NurseInfoDTO();
        // nurseMain
        dto.setId(nurseMain.getId());
        dto.setFirstName(nurseMain.getFirstName());
        dto.setMiddleName(nurseMain.getMiddleName());
        dto.setLastName(nurseMain.getLastName());
        dto.setDept(nurseMain.getDept());
        dto.setRank(nurseMain.getRank());
        dto.setGender(nurseMain.getGender());
        dto.setDateOfBirth(nurseMain.getDateOfBirth());
        dto.setHireDate(nurseMain.getHireDate());
        dto.setSts(nurseMain.getSts());
        dto.setWt(nurseMain.getWt());
        dto.setWriter(nurseMain.getWriter());
        dto.setCreateDate(nurseMain.getCreateDate());
        dto.setModifier(nurseMain.getModifier());
        dto.setModifyDate(nurseMain.getModifyDate());

        // nurseInformation
        dto.setTel(nurseInformation.getTel());
        dto.setEmgcCntc(nurseInformation.getEmgcCntc());
        dto.setEmgcFName(nurseInformation.getEmgcFName());
        dto.setEmgcMName(nurseInformation.getEmgcMName());
        dto.setEmgcLName(nurseInformation.getEmgcLName());
        dto.setEmgcRel(nurseInformation.getEmgcRel());
        dto.setEmgcNote(nurseInformation.getEmgcNote());
        dto.setEmail(nurseInformation.getEmail());
        dto.setPcd(nurseInformation.getPcd());
        dto.setDefAdd(nurseInformation.getDefAdd());
        dto.setDetAdd(nurseInformation.getDetAdd());
        dto.setRnNo(nurseInformation.getRnNo());
        dto.setEdbc(nurseInformation.getEdbc());
        dto.setGradDate(nurseInformation.getGradDate());
        dto.setFl(nurseInformation.getFl());
        dto.setMs(nurseInformation.getMs());
        dto.setNatn(nurseInformation.getNatn());
        dto.setDss(nurseInformation.getDss());
        dto.setCarr(nurseInformation.getCarr());
        dto.setPicture(nurseInformation.getPicture());
        dto.setNote(nurseInformation.getNote());

        model.addAttribute("nurseInfo", dto);

        return "nurse/nurse_information";
    }
}
