package com.HPMS.HPMS.nurse.nurseinformation;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import com.HPMS.HPMS.nurse.nursemain.NurseMainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class NurseInformationService {

    private final NurseInformationRepository nurseInformationRepository;
    private final NurseMainRepository nurseMainRepository;

    public NurseInformation create (NurseMain nurseMain, String firstName, String lastName, String middleName, String tel, String emgcCntc, String emgcFName, String emgcLName, String emgcMName, String emgcRel, String emgcNote, String email, Integer pcd, String defAdd, String detAdd, String rnNo, String edbc, Integer gradDate, String fl, String ms, String natn, String dss, String carr, String picture, String note) {
        NurseInformation nurseInformation = new NurseInformation();
        nurseInformation.setFirstName(firstName);
        nurseInformation.setLastName(lastName);
        nurseInformation.setMiddleName(middleName);
        nurseInformation.setTel(tel);
        nurseInformation.setEmgcCntc(emgcCntc);
        nurseInformation.setEmgcFName(emgcFName);
        nurseInformation.setEmgcLName(emgcLName);
        nurseInformation.setEmgcMName(emgcMName);
        nurseInformation.setEmgcRel(emgcRel);
        nurseInformation.setEmgcNote(emgcNote);
        nurseInformation.setEmail(email);
        nurseInformation.setPcd(pcd);
        nurseInformation.setDefAdd(defAdd);
        nurseInformation.setDetAdd(detAdd);
        nurseInformation.setRnNo(rnNo);
        nurseInformation.setEdbc(edbc);
        nurseInformation.setGradDate(gradDate);
        nurseInformation.setFl(fl);
        nurseInformation.setMs(ms);
        nurseInformation.setNatn(natn);
        nurseInformation.setDss(dss);
        nurseInformation.setCarr(carr);
        nurseInformation.setPicture(picture);
        nurseInformation.setNote(note);
        nurseInformation.setNurseMain(nurseMain);
        return this.nurseInformationRepository.save(nurseInformation);
    }

    public NurseInformation findByNurseMainId(Integer id) {
        return nurseInformationRepository.findByNurseMainId(id)
                .orElse(null);
    }

    @Transactional
    public void saveWithMainAndFile(Integer nurseId, NurseInformation info, MultipartFile file) throws IOException {
        NurseMain main = nurseMainRepository.findById(nurseId)
                .orElseThrow(() -> new RuntimeException("NurseMain not found"));

        // 사진 업로드 처리
        if (!file.isEmpty()) {
            String uploadDir = "uploads/nurse/"; // 실제 저장 경로
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String filePath = uploadDir + file.getOriginalFilename();
            file.transferTo(new File(filePath));
            info.setPicture(filePath);
        }

        info.setNurseMain(main);
        main.setNurseInformation(info);

        nurseInformationRepository.save(info);
    }
}
