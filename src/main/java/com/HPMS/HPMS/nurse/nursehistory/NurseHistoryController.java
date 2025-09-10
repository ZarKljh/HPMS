package com.HPMS.HPMS.nurse.nursehistory;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nurse/{nurseId}/history")
@RequiredArgsConstructor
public class NurseHistoryController {

    private final NurseHistoryService historyService;

    @GetMapping("")
    public ResponseEntity<List<com.HPMS.HPMS.nurse.history.NurseHistory>> getHistories(@PathVariable Integer nurseId) {
        return ResponseEntity.ok(historyService.getHistoriesByNurse(nurseId));
    }

    @PostMapping("")
    public ResponseEntity<com.HPMS.HPMS.nurse.history.NurseHistory> createHistory(@PathVariable Integer nurseId,
                                                                                  @RequestBody com.HPMS.HPMS.nurse.history.NurseHistory history) {
        return ResponseEntity.ok(historyService.createHistory(nurseId, history));
    }

    @PutMapping("/{historyId}")
    public ResponseEntity<com.HPMS.HPMS.nurse.history.NurseHistory> updateHistory(@PathVariable Integer historyId,
                                                                                  @RequestBody com.HPMS.HPMS.nurse.history.NurseHistory updatedHistory) {
        return ResponseEntity.ok(historyService.updateHistory(historyId, updatedHistory));
    }

    @DeleteMapping("/{historyId}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Integer historyId) {
        historyService.deleteHistory(historyId);
        return ResponseEntity.noContent().build();
    }
}
