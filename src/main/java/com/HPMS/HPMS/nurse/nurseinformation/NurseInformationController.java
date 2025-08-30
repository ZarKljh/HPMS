package com.HPMS.HPMS.nurse.nurseinformation;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/nurse")
@RequiredArgsConstructor
@Controller
public class NurseInformationController {

    private final NurseMainService nurseMainService;
    private final NurseInformationService nurseInformationService;

    @PostMapping("/create/{nurseId}")
    public String createNurseInformation(Model model, @PathVariable("nurseId") Integer nurseId, @RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName, @RequestParam(value="middleName") String middleName, @RequestParam(value="tel") String tel, @RequestParam(value="emgcCntc") String emgcCntc, @RequestParam(value="emgcFName") String emgcFName, @RequestParam(value="emgcLName") String emgcLName, @RequestParam(value="emgcMName") String emgcMName, @RequestParam(value="emgcRel") String emgcRel, @RequestParam(value="emgcNote") String emgcNote, @RequestParam(value="email") String email, @RequestParam(value="pcd") Integer pcd, @RequestParam(value="defAdd") String defAdd, @RequestParam(value="detAdd") String detAdd, @RequestParam(value="rnNo") String rnNo, @RequestParam(value="edbc") String edbc, @RequestParam(value="gradDate") Integer gradDate, @RequestParam(value="fl") String fl, @RequestParam(value="ms") String ms, @RequestParam(value="natn") String natn, @RequestParam(value="dss") String dss, @RequestParam(value="carr") String carr, @RequestParam(value="picture") String picture, @RequestParam(value="note") String note) {
        NurseMain nurseMain = this.nurseMainService.getNurseMain(nurseId);
        NurseInformation nurseInformation = this.nurseInformationService.create(nurseMain, firstName, lastName, middleName, tel, emgcCntc, emgcFName, emgcLName, emgcMName, emgcRel, emgcNote, email, pcd, defAdd, detAdd, rnNo, edbc, gradDate, fl, ms, natn, dss, carr, picture, note);
        model.addAttribute("nurseInformation", nurseInformation);
        return String.format("redirect:/nurse/info/%s", nurseId);
    }
}
