package com.HPMS.HPMS.Doctor.DoctorH;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctor/history")
public class DoctorHController {
    private final DoctorHService doctorHService;
    private final DoctorHRepository doctorHRepository;

    @GetMapping("")
    public String list(@ModelAttribute("cond") DoctorHForm cond,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "20") int size,
                       @RequestParam(defaultValue = "createDate,DESC") String sort,
                       Model model) {
        Sort sortObj = Sort.by(Sort.Order.desc("createDate")); // 기본
        if (!sort.isBlank()) {
            String[] parts = sort.split(",");
            sortObj = (parts.length == 2 && "ASC".equalsIgnoreCase(parts[1]))
                    ? Sort.by(Sort.Order.asc(parts[0]))
                    : Sort.by(Sort.Order.desc(parts[0]));
        }
        Pageable pageable = PageRequest.of(page, size, sortObj);
        model.addAttribute("page", doctorHService.search(cond, pageable));
        return "doctor/doctor_history";
    }
    @GetMapping("/{hid}")
    public String detail(@PathVariable Integer hid, Model model, RedirectAttributes redirect) {
        return doctorHRepository.findById(hid)
                .map(h -> {
                    model.addAttribute("h", h);
                    return "doctor/doctor_history_detail"; // 템플릿 경로
                })
                .orElseGet(() -> {
                    redirect.addFlashAttribute("msg", "해당 히스토리가 없습니다.");
                    return "redirect:/doctor/history";
                });
    }
}
