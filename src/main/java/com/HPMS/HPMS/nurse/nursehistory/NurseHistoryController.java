package com.HPMS.HPMS.nurse.nursehistory;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainService;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("")
    public String showAllHistory(Model model) {
        List<NurseHistory> historyList = nurseHistoryService.getAllHistoryList();
        model.addAttribute("historyList", historyList);
        return "nurse/nurse_history";
    }

    // 특정 간호사 히스토리
    @GetMapping("/{nurseId}")
    public String showNurseHistory(@PathVariable Integer nurseId, Model model) {
        NurseMain nurseMain = nurseMainService.getNurseMain(nurseId);
        List<NurseHistory> historyList = nurseHistoryService.getHistoryByNurse(nurseMain);
        model.addAttribute("historyList", historyList);
        model.addAttribute("nurseMain", nurseMain);
        return "nurse/nurse_history_detail";
    }

    @GetMapping("/detail/{historyId}")
    public String showNurseHistoryDetail(@PathVariable Integer historyId, Model model) {
        NurseHistory history = nurseHistoryService.getHistoryById(historyId);
        NurseMain nurseMain = history.getNurse(); // history에 매핑된 nurse 가져오기
        model.addAttribute("history", history);
        model.addAttribute("nurseMain", nurseMain);
        return "nurse/nurse_history_detail";
    }

    // 수정자별 히스토리
    @GetMapping("/modifier/{modifier}")
    public String showHistoryByModifier(@PathVariable String modifier, Model model) {
        List<NurseHistory> historyList = nurseHistoryService.getHistoryByModifier(modifier);
        model.addAttribute("historyList", historyList);
        model.addAttribute("modifier", modifier);
        return "nurse/nurse_history";
    }

    // 이름으로 검색
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