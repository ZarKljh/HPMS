package com.HPMS.HPMS.nurse.nursehistory;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NurseHistoryService {
    private final NurseHistoryRepository historyRepository;
    private final NurseMainRepository nurseMainRepository;

    public List<com.HPMS.HPMS.nurse.history.NurseHistory> getHistoriesByNurse(Integer nurseId) {
        return historyRepository.findByNurseMain_Id(nurseId);
    }

    public com.HPMS.HPMS.nurse.history.NurseHistory createHistory(Integer nurseId, com.HPMS.HPMS.nurse.history.NurseHistory history) {
        NurseMain nurse = nurseMainRepository.findById(nurseId)
                .orElseThrow(() -> new IllegalArgumentException("Nurse not found with id: " + nurseId));
        history.setNurseMain(nurse);
        return historyRepository.save(history);
    }

    public com.HPMS.HPMS.nurse.history.NurseHistory updateHistory(Integer id, com.HPMS.HPMS.nurse.history.NurseHistory updatedHistory) {
        return historyRepository.findById(id)
                .map(history -> {
                    history.setNote(updatedHistory.getNote());
                    history.setModifier(updatedHistory.getModifier());
                    history.setModifyDate(updatedHistory.getModifyDate());
                    return historyRepository.save(history);
                })
                .orElseThrow(() -> new IllegalArgumentException("History not found with id: " + id));
    }

    public void deleteHistory(Integer id) {
        historyRepository.deleteById(id);
    }
}
