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

    private final DoctorDTLService doctorDTLService;
    private final DoctorMService doctorMService;

    /**
     * 의사 상세(메인+디테일)
     * 예: GET /doctor/detail?id=123
     */
    @GetMapping("/doctor/detail")
    public String detailPage(@RequestParam("id") Integer doctorId,
                             Model model,
                             RedirectAttributes redirect) {
        try {
            // 메인 필수
            DoctorM main = doctorMService.get(doctorId);
            model.addAttribute("main", main);

            // 디테일(Optional) - 없으면 빈 객체로 내려서 템플릿 NPE 방지
            DoctorDTL detail;
            try {
                detail = doctorDTLService.getByDoctorId(doctorId);
            } catch (Exception ignore) {
                detail = new DoctorDTL();
                detail.setDoctorMain(main); // 화면에서 참조할 수 있도록 연결
            }
            model.addAttribute("detail", detail);

            return "doctor/doctor_detail";
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", "해당 의사 정보가 없습니다.");
            return "redirect:/doctor";
        }
    }

    /**
     * 국적 저장 (팝업에서 선택 후 자동 제출)
     * 예: POST /doctor/{id}/nationality  (body: iso2, countryKr)
     */
    @PostMapping("/doctor/{id}/nationality")
    @ResponseBody
    public String updateNationality(@PathVariable Integer id,
                                    @RequestParam String iso2,
                                    @RequestParam String countryKr) {
        doctorDTLService.updateNationalityByDoctorId(id, iso2, countryKr);
        return "OK"; // 리다이렉트/뷰 반환 X (iframe 안에서 200 OK만 받음)
    }

}
