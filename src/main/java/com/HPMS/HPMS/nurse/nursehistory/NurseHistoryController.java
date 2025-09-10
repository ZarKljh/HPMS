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
@RequestMapping("/nurse")
@RequiredArgsConstructor
public class NurseHistoryController {

    private final NurseHistoryService nurseHistoryService;
    private final NurseMainService nurseMainService;

    //전체 간호사 수정 히스토리 조회
    @GetMapping("/history")
    public String showAllHistory(Model model) {
        List<NurseHistory> historyList = nurseHistoryService.getAllHistoryList();
        model.addAttribute("historyList", historyList);
        return "nurse/nurse_history";
    }

    //특정 간호사의 히스토리 조회
    @GetMapping("/history/{nurseId}")
    public String showNurseHistory(@PathVariable Integer nurseId, Model model) {
        try {
            NurseMain nurseMain = nurseMainService.getNurseMain(nurseId);
            List<NurseHistory> historyList = nurseHistoryService.getHistoryByNurse(nurseMain);

            model.addAttribute("historyList", historyList);
            model.addAttribute("nurseMain", nurseMain);
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "데이터를 불러오는 중 오류가 발생했습니다: " + e.getMessage());
        }

        return "nurse/nurse_history_detail";
    }

    //특정 간호사의 히스토리 조회 (detail 경로)
    @GetMapping("/history/detail/{nurseId}")
    public String showNurseHistoryDetail(@PathVariable Integer nurseId, Model model) {
        return showNurseHistory(nurseId, model);
    }

    //수정자별 히스토리 조회
    @GetMapping("/history/modifier/{modifier}")
    public String showHistoryByModifier(@PathVariable String modifier, Model model) {
        List<NurseHistory> historyList = nurseHistoryService.getHistoryByModifier(modifier);
        model.addAttribute("historyList", historyList);
        model.addAttribute("modifier", modifier);
        return "nurse/nurse_history";
    }

    //간호사 이름으로 히스토리 검색
    @GetMapping("/history/search")
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