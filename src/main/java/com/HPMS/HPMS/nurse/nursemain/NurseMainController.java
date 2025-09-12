package com.HPMS.HPMS.nurse.nursemain;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequestMapping("/nurse")
@RequiredArgsConstructor
@Controller
public class NurseMainController {

    private final NurseMainService nurseMainService;

//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page, @RequestParam(value="size", defaultValue="10") int size, HttpSession session, @RequestParam(value = "kw", defaultValue = "") String kw) {
        session.removeAttribute("tempNurseMain");

        // Page 객체 가져오기
        Page<NurseMain> paging = this.nurseMainService.getList(page, size, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("size", size);

        // 페이지 번호 리스트 생성
        int totalPages = paging.getTotalPages();
        List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)
                .boxed()
                .collect(Collectors.toList());

        int currentPage = paging.getNumber();

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageNumbers", pageNumbers);

        return "nurse/nurse_main";
    }

//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("/create")
    public String createMainForm(Model model) {
        model.addAttribute("nurseMain", new NurseMain());
        return "nurse/nurse_main_form";
    }

//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @PostMapping("/create")
    public String createMain(@ModelAttribute NurseMain nurseMain, HttpSession session) {
        LocalDateTime now = LocalDateTime.now();
        nurseMain.setCreateDate(now);
        nurseMain.setModifyDate(now);

        session.setAttribute("tempNurseMain", nurseMain);
        return "redirect:/nurse/create/info";
    }
}
