package com.HPMS.HPMS.nurse.nursemain;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequestMapping("/nurse")
@RequiredArgsConstructor
@Controller
public class NurseMainController {

    private final NurseMainService nurseMainService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page, @RequestParam(value="size", defaultValue="10") int size, HttpSession session, @RequestParam(value = "kw", defaultValue = "") String kw) {
        session.removeAttribute("tempNurseMain");

        // Page 객체 가져오기
        Page<NurseMain> paging = this.nurseMainService.getList(page, size, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("size", size);

        long totalCount = paging.getTotalElements();
        model.addAttribute("totalCount", totalCount);

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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("/create")
    public String createMainForm(Model model) {
        model.addAttribute("nurseMain", new NurseMain());
        return "nurse/nurse_main_form";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @PostMapping("/create")
    public String createMain(@ModelAttribute NurseMain nurseMain, HttpSession session) {
        LocalDateTime now = LocalDateTime.now();
        nurseMain.setCreateDate(now);
        nurseMain.setModifyDate(now);

        session.setAttribute("tempNurseMain", nurseMain);
        return "redirect:/nurse/create/info";
    }

    // 다중 삭제 처리
    @PostMapping("/deleteMultiple")
    public String deleteMultipleNurses(
            @RequestParam(value = "checkedIds", required = false) List<Integer> checkedIds,
            RedirectAttributes redirectAttributes) {

        System.out.println("========== 삭제 요청 시작 ==========");
        System.out.println("받은 checkedIds: " + checkedIds);
        System.out.println("checkedIds null 여부: " + (checkedIds == null));
        System.out.println("checkedIds 크기: " + (checkedIds != null ? checkedIds.size() : "null"));

        try {
            if (checkedIds == null || checkedIds.isEmpty()) {
                System.out.println("ERROR: checkedIds가 null이거나 비어있음");
                redirectAttributes.addFlashAttribute("errorMessage", "삭제할 항목을 선택해주세요.");
                return "redirect:/nurse";
            }

            System.out.println("삭제할 ID들: " + checkedIds);

            // 실제 삭제 전에 데이터 존재 확인
            for (Integer id : checkedIds) {
                NurseMain nurse = nurseMainService.findById(id);
                System.out.println("ID " + id + "의 간호사: " + (nurse != null ? nurse.getFirstName() : "없음"));
            }

            // 선택된 간호사들 삭제
            nurseMainService.deleteMultipleNurses(checkedIds);

            System.out.println("삭제 완료!");

            redirectAttributes.addFlashAttribute("successMessage",
                    checkedIds.size() + "명의 간호사 정보가 삭제되었습니다.");

        } catch (Exception e) {
            System.out.println("ERROR: 삭제 중 오류 발생");
            System.out.println("오류 메시지: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage",
                    "삭제 중 오류가 발생했습니다: " + e.getMessage());
        }

        System.out.println("========== 삭제 요청 끝 ==========");
        return "redirect:/nurse";
    }
}
