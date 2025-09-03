package com.HPMS.HPMS.Doctor.DoctorDTL;

import com.HPMS.HPMS.Doctor.DoctorM.DoctorM;
import com.HPMS.HPMS.Doctor.DoctorM.DoctorMService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class DoctorDTLController {

    private final DoctorDTLService service;
    private final DoctorMService doctorMService;

    /** 더보기: 메인+디테일 함께 표시, 하단에 수정/삭제 버튼 */
    @GetMapping("/doctor/detail")
    public String detailPage(@RequestParam("id") Integer doctorId,
                             Model model,
                             RedirectAttributes redirect) {
        try {
            DoctorM main = doctorMService.get(doctorId);
            model.addAttribute("main", main);

            DoctorDTL detail = null;
            try { detail = service.getByDoctorId(doctorId); } catch (Exception ignore) {}
            model.addAttribute("detail", detail);

            return "doctor/doctor_detail";
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", "해당 의사 정보가 없습니다.");
            return "redirect:/doctor";
        }
    }
}
