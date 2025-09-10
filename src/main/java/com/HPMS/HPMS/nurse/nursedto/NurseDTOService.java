package com.HPMS.HPMS.nurse.nursedto;

import com.HPMS.HPMS.nurse.nursehistory.NurseHistoryService;
import com.HPMS.HPMS.nurse.nurseinformation.NurseInformation;
import com.HPMS.HPMS.nurse.nurseinformation.NurseInformationRepository;
import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NurseDTOService {

    private final NurseMainRepository nurseMainRepository;
    private final NurseInformationRepository nurseInformationRepository;
    private final NurseHistoryService nurseHistoryService;

    public NurseDTO getNurseDTO(Integer nurseId) {
        Optional<NurseMain> nmOpt = nurseMainRepository.findById(nurseId);
        if (nmOpt.isEmpty()) return null;
        NurseMain nm = nmOpt.get();

        NurseMainDTO mainDTO = new NurseMainDTO(
                nm.getId(),
                nm.getFirstName(),
                nm.getMiddleName(),
                nm.getLastName(),
                nm.getDept(),
                nm.getRank(),
                nm.getGender(),
                nm.getDateOfBirth(),
                nm.getHireDate(),
                nm.getSts(),
                nm.getWt(),
                nm.getWriter(),
                nm.getCreateDate(),
                nm.getModifier(),
                nm.getModifyDate()
        );

        Optional<NurseInformation> niOpt = nurseInformationRepository.findByNurseMainId(nurseId);
        NurseInformationDTO infoDTO = niOpt.map(ni -> new NurseInformationDTO(
                ni.getFirstName(),
                ni.getLastName(),
                ni.getMiddleName(),
                ni.getTel(),
                ni.getEmgcCntc(),
                ni.getEmgcFName(),
                ni.getEmgcLName(),
                ni.getEmgcMName(),
                ni.getEmgcRel(),
                ni.getEmgcNote(),
                ni.getEmail(),
                ni.getPcd(),
                ni.getDefAdd(),
                ni.getDetAdd(),
                ni.getRnNo(),
                ni.getEdbc(),
                ni.getGradDate(),
                ni.getFl(),
                ni.getMs(),
                ni.getNatn(),
                ni.getDss(),
                ni.getCarr(),
                ni.getPicture(),
                ni.getNote()
        )).orElse(null);

        return new NurseDTO(mainDTO, infoDTO);
    }

    @Transactional
    public void updateNurse(Integer nurseId, NurseDTO dto) {
        NurseMain nm = nurseMainRepository.findById(nurseId)
                .orElseThrow(() -> new RuntimeException("간호사 기본 정보 없음"));

        // NurseMain 수정
        NurseMainDTO m = dto.getNurseMain();
        if (m != null) {
            nm.setFirstName(m.getFirstName() != null ? m.getFirstName() : nm.getFirstName());
            nm.setMiddleName(m.getMiddleName() != null ? m.getMiddleName() : nm.getMiddleName());
            nm.setLastName(m.getLastName() != null ? m.getLastName() : nm.getLastName());
            nm.setDept(m.getDept() != null ? m.getDept() : nm.getDept());
            nm.setRank(m.getRank() != null ? m.getRank() : nm.getRank());
            nm.setGender(m.getGender() != null ? m.getGender() : nm.getGender());
            nm.setDateOfBirth(m.getDateOfBirth() != null ? m.getDateOfBirth() : nm.getDateOfBirth());
            nm.setHireDate(m.getHireDate() != null ? m.getHireDate() : nm.getHireDate());
            nm.setSts(m.getSts() != null ? m.getSts() : nm.getSts());
            nm.setWt(m.getWt() != null ? m.getWt() : nm.getWt());
            nm.setModifier(m.getModifier() != null ? m.getModifier() : nm.getModifier());

            nm.setModifyDate(LocalDateTime.now());
            nurseMainRepository.save(nm);
        }

        // NurseInformation 수정
        NurseInformationDTO i = dto.getNurseInformation();
        if (i != null) {
            NurseInformation ni = nurseInformationRepository.findByNurseMainId(nurseId)
                    .orElseGet(() -> {
                        NurseInformation newInfo = new NurseInformation();
                        newInfo.setNurseMain(nm);
                        nm.setNurseInformation(newInfo);
                        return newInfo;
                    });

            // 기존 값 유지하면서 DTO 값이 있으면 덮어쓰기
            ni.setFirstName(i.getFirstName() != null ? i.getFirstName() : ni.getFirstName());
            ni.setLastName(i.getLastName() != null ? i.getLastName() : ni.getLastName());
            ni.setMiddleName(i.getMiddleName() != null ? i.getMiddleName() : ni.getMiddleName());
            ni.setTel(i.getTel() != null ? i.getTel() : ni.getTel());
            ni.setEmgcCntc(i.getEmgcCntc() != null ? i.getEmgcCntc() : ni.getEmgcCntc());
            ni.setEmgcFName(i.getEmgcFName() != null ? i.getEmgcFName() : ni.getEmgcFName());
            ni.setEmgcLName(i.getEmgcLName() != null ? i.getEmgcLName() : ni.getEmgcLName());
            ni.setEmgcMName(i.getEmgcMName() != null ? i.getEmgcMName() : ni.getEmgcMName());
            ni.setEmgcRel(i.getEmgcRel() != null ? i.getEmgcRel() : ni.getEmgcRel());
            ni.setEmgcNote(i.getEmgcNote() != null ? i.getEmgcNote() : ni.getEmgcNote());
            ni.setEmail(i.getEmail() != null ? i.getEmail() : ni.getEmail());
            ni.setPcd(i.getPcd() != null ? i.getPcd() : ni.getPcd());
            ni.setDefAdd(i.getDefAdd() != null ? i.getDefAdd() : ni.getDefAdd());
            ni.setDetAdd(i.getDetAdd() != null ? i.getDetAdd() : ni.getDetAdd());
            ni.setRnNo(i.getRnNo() != null ? i.getRnNo() : ni.getRnNo());
            ni.setEdbc(i.getEdbc() != null ? i.getEdbc() : ni.getEdbc());
            ni.setGradDate(i.getGradDate() != null ? i.getGradDate() : ni.getGradDate());
            ni.setFl(i.getFl() != null ? i.getFl() : ni.getFl());
            ni.setMs(i.getMs() != null ? i.getMs() : ni.getMs());
            ni.setNatn(i.getNatn() != null ? i.getNatn() : ni.getNatn());
            ni.setDss(i.getDss() != null ? i.getDss() : ni.getDss());
            ni.setCarr(i.getCarr() != null ? i.getCarr() : ni.getCarr());
            ni.setPicture(i.getPicture() != null ? i.getPicture() : ni.getPicture());
            ni.setNote(i.getNote() != null ? i.getNote() : ni.getNote());

            nurseInformationRepository.save(ni);
        }

        // 🔥 히스토리 저장 (수정 완료 후)
        String modifier = m != null ? m.getModifier() : "system";
        nurseHistoryService.saveNurseHistory(nm, i, modifier);
    }

    public void delete(NurseDTO nurseDTO) {
        NurseMain nurseMain = nurseMainRepository.findById(nurseDTO.getNurseMain().getId())
                .orElseThrow(() -> new RuntimeException("삭제할 간호사가 없습니다."));
        nurseMainRepository.delete(nurseMain);
    }
}
