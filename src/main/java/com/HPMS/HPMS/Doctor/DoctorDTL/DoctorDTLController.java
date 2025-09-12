// src/main/java/com/HPMS/HPMS/Doctor/DoctorDTL/DoctorDTLController.java
package com.HPMS.HPMS.Doctor.DoctorDTL;

import com.HPMS.HPMS.Doctor.DoctorM.DoctorM;
import com.HPMS.HPMS.Doctor.DoctorM.DoctorMForm;
import com.HPMS.HPMS.Doctor.DoctorM.DoctorMService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctor/dtl")  // ✅ DTL용 prefix 부여 (충돌 방지)
public class DoctorDTLController {

    private final DoctorDTLService doctorDTLService;
    private final DoctorMService doctorMService;

    /** 상세 페이지 */
    @GetMapping("/detail")
    public String detailPage(@RequestParam("id") Integer doctorId,
                             Model model,
                             RedirectAttributes redirect) {
        try {
            DoctorM main = doctorMService.get(doctorId);
            model.addAttribute("main", main);

            DoctorDTL detail;
            try {
                detail = doctorDTLService.getByDoctorId(doctorId);
            } catch (Exception ignore) {
                detail = new DoctorDTL();
                detail.setDoctorMain(main);
            }
            model.addAttribute("detail", detail);

            return "doctor/doctor_detail";
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", "해당 의사 정보가 없습니다.");
            return "redirect:/doctor";
        }
    }

    /** 수정 폼 */
    @GetMapping("/edit")
    public String editPage(@RequestParam("id") Integer doctorId,
                           Model model,
                           RedirectAttributes redirect) {
        try {
            DoctorM main = doctorMService.get(doctorId);
            model.addAttribute("doctorId", doctorId);
            model.addAttribute("mForm", DoctorMForm.fromEntity(main));

            DoctorDTL detail;
            try {
                detail = doctorDTLService.getByDoctorId(doctorId);
            } catch (Exception ignore) {
                detail = new DoctorDTL();
                detail.setDoctorMain(main);
            }
            model.addAttribute("dForm", DoctorDTLForm.fromEntity(detail));
            return "doctor/doctor_edit";
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", "해당 의사 정보가 없습니다.");
            return "redirect:/doctor";
        }
    }

    /** 저장 */
    @PostMapping("/edit/{id}") // ✅ 최종 경로: /doctor/dtl/edit/{id}
    public String update(@PathVariable Integer id,
                         @ModelAttribute("mForm") DoctorMForm mForm,
                         @ModelAttribute("dForm") DoctorDTLForm dForm,
                         RedirectAttributes redirect) {
        doctorDTLService.updateMainAndDetail(id, mForm, dForm);
        redirect.addFlashAttribute("success", "수정되었습니다.");
        return "redirect:/doctor/dtl/detail?id=" + id; // ✅ 상세도 DTL prefix로
    }
}
