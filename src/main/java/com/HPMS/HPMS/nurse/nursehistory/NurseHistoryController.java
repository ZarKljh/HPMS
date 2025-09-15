package com.HPMS.HPMS.nurse.nursehistory;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/nurse/history")
@RequiredArgsConstructor
public class NurseHistoryController {

    private final NurseHistoryService nurseHistoryService;
    private final NurseMainService nurseMainService;

    // 전체 히스토리
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("")
    public String showAllHistory(Model model) {
        List<NurseHistory> historyList = nurseHistoryService.getAllHistoryList();
        model.addAttribute("historyList", historyList);
        return "nurse/nurse_history";
    }

    // 특정 간호사 히스토리
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("/{nurseId}")
    public String showNurseHistory(@PathVariable Integer nurseId, Model model) {
        try {
            NurseMain nurseMain = nurseMainService.getNurseMain(nurseId);
            List<NurseHistory> historyList = nurseHistoryService.getHistoryByNurse(nurseMain);
            model.addAttribute("historyList", historyList);
            model.addAttribute("nurseMain", nurseMain);
            return "nurse/nurse_history_detail";
        } catch (Exception e) {
            model.addAttribute("error", "간호사 정보를 찾을 수 없습니다.");
            return "redirect:/nurse/history";
        }
    }

    // 단일 히스토리 상세보기 - 수정된 버전
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("/detail/{historyId}")
    public String showNurseHistoryDetail(@PathVariable Integer historyId, Model model) {
        try {
            NurseHistory history = nurseHistoryService.getHistoryById(historyId);

            // Lazy Loading 문제 해결을 위해 명시적으로 nurseMain 로드
            NurseMain nurseMain = history.getNurseMain(); // getNurse() 대신 getNurseMain() 사용

            // 디버깅을 위한 로그 추가
            System.out.println("History ID: " + history.getId());
            System.out.println("NurseMain: " + (nurseMain != null ? nurseMain.getId() : "null"));
            System.out.println("First Name: " + history.getFirstName());

            // 단일 히스토리를 리스트로 감싸기
            List<NurseHistory> historyList = Arrays.asList(history);

            model.addAttribute("historyList", historyList);
            model.addAttribute("nurseMain", nurseMain);

            return "nurse/nurse_history_detail";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "히스토리 정보를 불러올 수 없습니다: " + e.getMessage());
            return "redirect:/nurse/history";
        }
    }

    // 수정자별 히스토리
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("/modifier/{modifier}")
    public String showHistoryByModifier(@PathVariable String modifier, Model model) {
        List<NurseHistory> historyList = nurseHistoryService.getHistoryByModifier(modifier);
        model.addAttribute("historyList", historyList);
        model.addAttribute("modifier", modifier);
        return "nurse/nurse_history";
    }

    // 이름으로 검색
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM','ROLE_DOCTOR','ROLE_NURSE')")
    @GetMapping("/search")
    public String searchHistory(@RequestParam(required = false) String name, Model model) {
        List<NurseHistory> historyList;
        if (name != null && !name.trim().isEmpty()) {
            historyList = nurseHistoryService.searchHistoryByNurseName(name);
        } else {
            historyList = nurseHistoryService.getAllHistoryList();
        }
        model.addAttribute("historyList", historyList);
        model.addAttribute("searchName", name);
        return "nurse/nurse_history";
    }
}