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

import java.util.List;

@Controller
@RequestMapping("/nurse/history")
@RequiredArgsConstructor
public class NurseHistoryController {

    private final NurseHistoryService nurseHistoryService;
    private final NurseMainService nurseMainService;

    // 전체 히스토리
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM')")
    @GetMapping("")
    public String showAllHistory(Model model) {
        List<NurseHistory> historyList = nurseHistoryService.getAllHistoryList();
        model.addAttribute("historyList", historyList);
        return "nurse/nurse_history";
    }

    // 단일 히스토리 상세보기
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM')")
    @GetMapping("/detail/{historyId}")
    public String showNurseHistoryDetail(@PathVariable Integer historyId, Model model) {
        try {
            NurseHistory history = nurseHistoryService.getHistoryById(historyId);
            NurseMain nurseMain = history.getNurseMain(); // Lazy loading 해결용

            model.addAttribute("nurseHistory", history);
            model.addAttribute("nurseMain", nurseMain);

            return "nurse/nurse_history_detail";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "히스토리 정보를 불러올 수 없습니다: " + e.getMessage());
            return "redirect:/nurse/history";
        }
    }

    // 이름으로 검색
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM')")
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