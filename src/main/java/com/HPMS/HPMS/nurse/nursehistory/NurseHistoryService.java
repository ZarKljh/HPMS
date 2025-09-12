package com.HPMS.HPMS.nurse.nursehistory;

import com.HPMS.HPMS.nurse.nursedto.NurseInformationDTO;
import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NurseHistoryService {

    private final NurseHistoryRepository nurseHistoryRepository;

    // 간호사 정보 수정 시 히스토리 저장
    @Transactional
    public void saveNurseHistory(NurseMain nurseMain, NurseInformationDTO nurseInfo, String modifier) {
        NurseHistory history = NurseHistory.snapshotOf(nurseMain, nurseInfo);
        history.setModifier(modifier);
        history.setModifyDate(LocalDateTime.now());
        nurseHistoryRepository.save(history);
    }

    // 전체 히스토리 조회
    @Transactional(readOnly = true)
    public List<NurseHistory> getAllHistoryList() {
        return nurseHistoryRepository.findAllOrderByCreateDateDesc();
    }

    // 특정 간호사 히스토리 조회
    @Transactional(readOnly = true)
    public List<NurseHistory> getHistoryByNurse(NurseMain nurseMain) {
        return nurseHistoryRepository.findByNurseMainOrderByCreateDateDesc(nurseMain);
    }

    // 수정자별 조회
    @Transactional(readOnly = true)
    public List<NurseHistory> getHistoryByModifier(String modifier) {
        return nurseHistoryRepository.findByModifierOrderByCreateDateDesc(modifier);
    }

    // 이름으로 검색
    @Transactional(readOnly = true)
    public List<NurseHistory> searchHistoryByNurseName(String name) {
        return nurseHistoryRepository.findByNurseNameContaining(name);
    }

    // ID로 히스토리 조회 (JOIN FETCH 사용)
    @Transactional(readOnly = true)
    public NurseHistory getHistoryById(Integer historyId) {
        return nurseHistoryRepository.findByIdWithNurseMain(historyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 히스토리를 찾을 수 없습니다. ID: " + historyId));
    }
}