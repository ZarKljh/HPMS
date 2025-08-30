package com.HPMS.HPMS.nurse.NurseDTO;

import com.HPMS.HPMS.nurse.nurseinformation.NurseInformation;
import com.HPMS.HPMS.nurse.nurseinformation.NurseInformationRepository;
import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
