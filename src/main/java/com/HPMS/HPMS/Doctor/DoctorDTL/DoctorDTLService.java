package com.HPMS.HPMS.Doctor.DoctorDTL;

import com.HPMS.HPMS.Doctor.DoctorDTL.DoctorDTLForm;
import com.HPMS.HPMS.Doctor.DoctorH.DoctorH;
import com.HPMS.HPMS.Doctor.DoctorH.DoctorHRepository;
import com.HPMS.HPMS.Doctor.DoctorH.DoctorHService;
import com.HPMS.HPMS.Doctor.DoctorM.DoctorM;
import com.HPMS.HPMS.Doctor.DoctorM.DoctorMRepository;
import com.HPMS.HPMS.Doctor.DoctorM.DoctorMForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoctorDTLService {

    private final DoctorDTLRepository repository;
    private final DoctorMRepository doctorMRepository;
    private final DoctorHService doctorHService;

    public DoctorDTL get(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("디테일 정보가 없습니다."));
    }

    public DoctorDTL getByDoctorId(Integer doctorId) {
        return repository.findByDoctorMainId(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("해당 의사의 디테일 정보가 없습니다."));
    }

    /** 신규: 메인+디테일 동시 생성 */
    @Transactional
    public Integer createMainAndDetail(DoctorMForm m, DoctorDTLForm d) {
        // 메인
        DoctorM main = DoctorM.builder()
                .department(m.getDepartment())
                .medicalDepartment(m.getMedicalDepartment())
                .rank(m.getRank())
                .position(m.getPosition())
                .firstName(m.getFirstName())
                .lastName(m.getLastName())
                .middleName(m.getMiddleName())
                .gender(m.getGender())
                .dateOfBirth(m.getDateOfBirth())
                .telephone(m.getTelephone())
                .hireDate(m.getHireDate())
                .status(m.getStatus())
                .workType(m.getWorkType())
                .build();
        DoctorM savedMain = doctorMRepository.save(main);

        // 디테일
        DoctorDTL detail = DoctorDTL.builder()
                .doctorMain(savedMain)
                .image(d.getImage())
                .userId(d.getUserId())
                .userPassword(d.getUserPassword())
                .officeTelephone(d.getOfficeTelephone())
                .emergencyContact(d.getEmergencyContact())
                .emergencyFirstName(d.getEmergencyFirstName())
                .emergencyLastName(d.getEmergencyLastName())
                .emergencyMiddleName(d.getEmergencyMiddleName())
                .emergencyRelation(d.getEmergencyRelation())
                .emergencyNote(d.getEmergencyNote())
                .email(d.getEmail())
                .postcode(d.getPostcode())
                .defaultAddress(d.getDefaultAddress())
                .detailedAddress(d.getDetailedAddress())
                .registrationNumber(d.getRegistrationNumber())
                .educationalBackground(d.getEducationalBackground())
                .foreignLanguage(d.getForeignLanguage())
                .license(d.getLicense())
                .major(d.getMajor())
                .workDependsOnExperience(d.getWorkDependsOnExperience())
                .awards(d.getAwards())
                .societyActivity(d.getSocietyActivity())
                .certificateOfSociety(d.getCertificateOfSociety())
                .militaryService(d.getMilitaryService())
                .nationality(d.getNationality())
                .disabilityStatus(d.getDisabilityStatus())
                .note(d.getNote())
                .writer("system")
                .build();
        repository.save(detail);

        savedMain.setDetail(detail);
        return savedMain.getId();
    }
    @Transactional
    public void updateNationalityByDoctorId(Integer doctorId, String iso2, String countryKr) {
        DoctorDTL detail = repository.findByDoctorMainId(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("해당 의사의 디테일 정보가 없습니다."));

        // 저장 형식은 원하는 대로 통일 (예: "대한민국 (KR)")
        detail.setNationality(countryKr + " (" + iso2 + ")");
        detail.setModifier("system");

        // readOnly=false 이므로 save가 실제로 반영됩니다
        repository.save(detail);
    }
    /** 수정: 메인+디테일 동시 수정 (디테일 없으면 생성) */
    @Transactional
    public void updateMainAndDetail(Integer doctorId, DoctorMForm m, DoctorDTLForm d) {
        DoctorM main = doctorMRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("의사 정보가 없습니다."));
        DoctorDTL detail = repository.findByDoctorMainId(doctorId).orElseGet(DoctorDTL::new);
        doctorHService.snapshotBeforeUpdate(main, detail);
        // 메인 갱신
        main.setDepartment(m.getDepartment());
        main.setMedicalDepartment(m.getMedicalDepartment());
        main.setRank(m.getRank());
        main.setPosition(m.getPosition());
        main.setFirstName(m.getFirstName());
        main.setLastName(m.getLastName());
        main.setMiddleName(m.getMiddleName());
        main.setGender(m.getGender());
        main.setDateOfBirth(m.getDateOfBirth());
        main.setTelephone(m.getTelephone());
        main.setHireDate(m.getHireDate());
        main.setStatus(m.getStatus());
        main.setWorkType(m.getWorkType());

        // 디테일 갱신/생성

        detail.setDoctorMain(main);
        detail.setImage(d.getImage());
        detail.setUserId(d.getUserId());
        if (d.getUserPassword() != null && !d.getUserPassword().isBlank()) {
            detail.setUserPassword(d.getUserPassword());
        }
        detail.setOfficeTelephone(d.getOfficeTelephone());
        detail.setEmergencyContact(d.getEmergencyContact());
        detail.setEmergencyFirstName(d.getEmergencyFirstName());
        detail.setEmergencyLastName(d.getEmergencyLastName());
        detail.setEmergencyMiddleName(d.getEmergencyMiddleName());
        detail.setEmergencyRelation(d.getEmergencyRelation());
        detail.setEmergencyNote(d.getEmergencyNote());
        detail.setEmail(d.getEmail());
        detail.setPostcode(d.getPostcode());
        detail.setDefaultAddress(d.getDefaultAddress());
        detail.setDetailedAddress(d.getDetailedAddress());
        detail.setRegistrationNumber(d.getRegistrationNumber());
        detail.setEducationalBackground(d.getEducationalBackground());
        detail.setForeignLanguage(d.getForeignLanguage());
        detail.setLicense(d.getLicense());
        detail.setMajor(d.getMajor());
        detail.setWorkDependsOnExperience(d.getWorkDependsOnExperience());
        detail.setAwards(d.getAwards());
        detail.setSocietyActivity(d.getSocietyActivity());
        detail.setCertificateOfSociety(d.getCertificateOfSociety());
        detail.setMilitaryService(d.getMilitaryService());
        if (d.getNationality() != null && !d.getNationality().isBlank()) {
            detail.setNationality(d.getNationality());
            detail.setDisabilityStatus(d.getDisabilityStatus());
            detail.setNote(d.getNote());
            detail.setModifier("system");

            repository.save(detail);
            main.setDetail(detail);
        }
    }
}
