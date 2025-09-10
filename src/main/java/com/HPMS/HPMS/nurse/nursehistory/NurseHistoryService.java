package com.HPMS.HPMS.nurse.nursehistory;

import com.HPMS.HPMS.nurse.license.License;
import com.HPMS.HPMS.nurse.license.LicenseService;
import com.HPMS.HPMS.nurse.nursedto.NurseInformationDTO;
import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NurseHistoryService {

    private final NurseHistoryRepository nurseHistoryRepository;
    private final LicenseService licenseService;

    //간호사 정보 수정 시 히스토리 저장
    public void saveNurseHistory(NurseMain nurseMain, NurseInformationDTO nurseInfo, String modifier) {
        // 첫 번째 라이센스 가져오기 (여러 개 있을 경우)
        List<License> licenses = licenseService.getByNurse(nurseMain);
        License firstLicense = licenses.isEmpty() ? null : licenses.get(0);

        // 히스토리 생성 및 저장
        NurseHistory history = NurseHistory.snapshotOf(nurseMain, nurseInfo, firstLicense);
        history.setModifier(modifier);
        history.setModifyDate(LocalDateTime.now());

        nurseHistoryRepository.save(history);
    }

    //전체 히스토리 조회 (페이징)
    public Page<NurseHistory> getAllHistory(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return nurseHistoryRepository.findAllByOrderByCreateDateDesc(pageable);
    }

    //특정 간호사의 히스토리 조회
    public List<NurseHistory> getHistoryByNurse(NurseMain nurseMain) {
        return nurseHistoryRepository.findByNurseMainOrderByCreateDateDesc(nurseMain);
    }

    //전체 히스토리 조회 (리스트)
    public List<NurseHistory> getAllHistoryList() {
        return nurseHistoryRepository.findAll().stream()
                .sorted((h1, h2) -> h2.getCreateDate().compareTo(h1.getCreateDate()))
                .toList();
    }

    //특정 기간 히스토리 조회
    public List<NurseHistory> getHistoryByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return nurseHistoryRepository.findByCreateDateBetween(startDate, endDate);
    }

    //수정자별 히스토리 조회
    public List<NurseHistory> getHistoryByModifier(String modifier) {
        return nurseHistoryRepository.findByModifierOrderByCreateDateDesc(modifier);
    }

    //간호사 이름으로 히스토리 검색
    public List<NurseHistory> searchHistoryByNurseName(String name) {
        return nurseHistoryRepository.findByNurseNameContaining(name);
    }
}