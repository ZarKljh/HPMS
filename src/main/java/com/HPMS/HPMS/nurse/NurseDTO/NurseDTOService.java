package com.HPMS.HPMS.nurse.NurseDTO;

import com.HPMS.HPMS.nurse.nurseinformation.NurseInformation;
import com.HPMS.HPMS.nurse.nurseinformation.NurseInformationRepository;
import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NurseDTOService {

    private final NurseMainRepository nurseMainRepository;
    private final NurseInformationRepository nurseInformationRepository;

    public NurseDTO getNurseDTO(Integer nurseId) {
        Optional<NurseMain> nmOpt = nurseMainRepository.findById(nurseId);
        if (nmOpt.isEmpty()) return null;
        NurseMain nm = nmOpt.get();

        String dateOfBirthStr = formatDate(nm.getDateOfBirth());
        String hireDateStr = formatDate(nm.getHireDate());

        NurseMainDTO mainDTO = new NurseMainDTO(
                nm.getId(),
                nm.getFirstName(),
                nm.getMiddleName(),
                nm.getLastName(),
                nm.getDept(),
                nm.getRank(),
                nm.getGender(),
                dateOfBirthStr,
                hireDateStr,
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
                formatDate(ni.getGradDate()),
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

    // Integer 19890315 -> "1989-03-15"
    private String formatDate(Integer date) {
        if (date == null) return null;
        String str = date.toString();
        if (str.length() != 8) return str;
        return str.substring(0,4) + "-" + str.substring(4,6) + "-" + str.substring(6,8);
    }

    public void updateNurse(Integer nurseId, NurseDTO dto) {
        NurseMain nm = nurseMainRepository.findById(nurseId)
                .orElseThrow(() -> new RuntimeException("간호사 기본 정보 없음"));

        // NurseMain 수정
        NurseMainDTO m = dto.getNurseMain();
        nm.setFirstName(m.getFirstName());
        nm.setMiddleName(m.getMiddleName());
        nm.setLastName(m.getLastName());
        nm.setDept(m.getDept());
        nm.setRank(m.getRank());
        nm.setGender(m.getGender());
        nm.setDateOfBirth(parseDate(m.getDateOfBirth()));
        nm.setHireDate(parseDate(m.getHireDate()));
        nm.setSts(m.getSts());
        nm.setWt(m.getWt());
        nm.setModifier(m.getModifier());
        nm.setModifyDate(LocalDateTime.now());
        nurseMainRepository.save(nm);

        // NurseInformation 수정
        NurseInformation ni = nurseInformationRepository.findByNurseMainId(nurseId)
                .orElse(new NurseInformation()); // 없으면 새로 생성
        NurseInformationDTO i = dto.getNurseInformation();
        if (i != null) {
            ni.setNurseMain(nm);
            ni.setFirstName(i.getFirstName());
            ni.setLastName(i.getLastName());
            ni.setMiddleName(i.getMiddleName());
            ni.setTel(i.getTel());
            ni.setEmgcCntc(i.getEmgcCntc());
            ni.setEmgcFName(i.getEmgcFName());
            ni.setEmgcLName(i.getEmgcLName());
            ni.setEmgcMName(i.getEmgcMName());
            ni.setEmgcRel(i.getEmgcRel());
            ni.setEmgcNote(i.getEmgcNote());
            ni.setEmail(i.getEmail());
            ni.setPcd(i.getPcd());
            ni.setDefAdd(i.getDefAdd());
            ni.setDetAdd(i.getDetAdd());
            ni.setRnNo(i.getRnNo());
            ni.setEdbc(i.getEdbc());
            ni.setGradDate(parseDate(i.getGradDate()));
            ni.setFl(i.getFl());
            ni.setMs(i.getMs());
            ni.setNatn(i.getNatn());
            ni.setDss(i.getDss());
            ni.setCarr(i.getCarr());
            ni.setPicture(i.getPicture());
            ni.setNote(i.getNote());
            nurseInformationRepository.save(ni);
        }
    }

    private Integer parseDate(String str) {
        if (str == null || str.isEmpty()) return null;
        return Integer.parseInt(str.replace("-", "")); // "1989-03-15" → 19890315
    }
}
