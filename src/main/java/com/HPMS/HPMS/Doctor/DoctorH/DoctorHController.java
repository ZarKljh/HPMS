package com.HPMS.HPMS.Doctor.DoctorH;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctor/history")
public class DoctorHController {
    private final DoctorHService doctorHService;


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
}
