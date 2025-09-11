// JobSimpleService.java
package com.HPMS.HPMS.global.Job_Code;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Job_Code_Service {
    private final Doc_Rank_Repository rankRepo;
    private final Doc_Position_Repository posRepo;

    public List<Job_Code_DTO> ranks() {
        return rankRepo.findAll(Sort.by(Sort.Order.asc("korName"))).stream()
                .map(e -> new Job_Code_DTO(
                        e.getId(),
                        e.getKorName(),      // ← 언더바 게터!!
                        e.getEngName(),      // ← 언더바 게터!!
                        e.getRank(),
                        "RANK"
                ))
                .toList();
    }

    public List<Job_Code_DTO> positions() {
        return posRepo.findAll(Sort.by(Sort.Order.asc("korName"))).stream()
                .map(e -> new Job_Code_DTO(
                        e.getId(),
                        e.getKorName(),      // ← 언더바 게터!!
                        e.getEngName(),      // ← 언더바 게터!!
                        e.getPosition(),
                        "POSITION"
                ))
                .toList();
    }
}
