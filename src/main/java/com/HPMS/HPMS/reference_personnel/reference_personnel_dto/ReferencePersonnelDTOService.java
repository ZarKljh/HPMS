package com.HPMS.HPMS.reference_personnel.reference_personnel_dto;

import com.HPMS.HPMS.reference_personnel.reference_personnel_dtl.ReferencePersonnelDtl;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dtl.ReferencePersonnelDtlRepository;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dtl.ReferencePersonnelDtlService;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dto.personnel_dtl_dto.ReferencePersonnelDtlDTO;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dto.personnel_m_dto.ReferencePersonnelMDTO;
import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelM;
import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelMRepository;
import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelMService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
// import java.util.logging.Logger;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReferencePersonnelDTOService {
    // 메인 엔티티 목록을 가져오는 서비스
    private final ReferencePersonnelMService referencePersonnelMService;
    // 상세 정보(DTL)를 가져오는 서비스
    private final ReferencePersonnelDtlService referencePersonnelDtlService;
    // private static final Logger log = LoggerFactory.getLogger(ReferencePersonnelDTOService.class);
    // @Slf4j롬복 대신사용 아래 디펜던시 추가 필수
    // implementation 'org.slf4j:slf4j-api:1.7.36' // 또는 최신 버전
    // implementation 'org.slf4j:slf4j-simple:1.7.36' // 콘솔 출력용

    // ReferencePersonnelM 리스트를 가져와서 DTO로 변환한 후 반환
    public List<ReferencePersonnelMDTO> getReferencePersonelMDTO(){
        // 관련자 정보 목록을 service 를 통해 DB에서 전체 목록을 가져온다.
        List<ReferencePersonnelM> referencePersonnelM  = referencePersonnelMService.getAllReferencePersonnelM();
        // html 출력을 위한 관련자 정보를 DTO
        // 오류지점 이 리스트는 ReferencePersonnelDtlDTO 타입인데, 아래에서 ReferencePersonnelMDTO 객체를 넣고 있어
        List<ReferencePersonnelMDTO> referencePersonnelMDTOList = new ArrayList<>();
        /*
        for (int i = 0; i < referencePersonnelM.size(); i++) {
            PatientM m = referencePersonnelM.get(i);
            // i를 활용한 로직 작성 가능
        }
        */
        for( ReferencePersonnelM m : referencePersonnelM){

            ReferencePersonnelDtl referencePersonnelDtl = referencePersonnelDtlService.getReferencePersonnelDtlByPersonnel(m);
            ReferencePersonnelMDTO referencePersonnelMDTO = new ReferencePersonnelMDTO();
            referencePersonnelMDTO.setId(m.getId());
            referencePersonnelMDTO.setNationality(m.getNationality());
            referencePersonnelMDTO.setFirstName(m.getFirstName());
            referencePersonnelMDTO.setLastName(m.getLastName());
            referencePersonnelMDTO.setMiddleName(m.getMiddleName());
            referencePersonnelMDTO.setEmail(m.getEmail());
            referencePersonnelMDTO.setCellPhone(m.getCellPhone());
            referencePersonnelMDTO.setCreator(m.getCreator());
            referencePersonnelMDTO.setCreateDate(m.getCreateDate());
            referencePersonnelMDTOList.add(referencePersonnelMDTO);
        }
        // return referencePersonnelMList; 
        return referencePersonnelMDTOList; //DTO 리스트를 반환
    }

    public ReferencePersonnelDtlDTO getReferencePersonnelDtlDTO(Integer id){

        ReferencePersonnelM referencePersonnelM = this.referencePersonnelMService.getReferencePersonnelM(id);
        // 상세정보를 가져오기 위한 DTO 객체 초기화
        ReferencePersonnelDtlDTO referencePersonnelDtlDTO = new ReferencePersonnelDtlDTO();

        ReferencePersonnelDtl referencePersonnelDtl = this.referencePersonnelDtlService.getReferencePersonnelDtlByPersonnel(referencePersonnelM);
        // 관련자 1명의 Integer id 값을 이용하여 상세정보를 가지고 온다.

        if (referencePersonnelDtl != null) {
            referencePersonnelDtlDTO.setPersonnel(referencePersonnelM);
            referencePersonnelDtlDTO.setId(referencePersonnelDtl.getId());
            referencePersonnelDtlDTO.setCompanyName(referencePersonnelDtl.getCompanyName());
            referencePersonnelDtlDTO.setDeptName(referencePersonnelDtl.getDeptName());
            referencePersonnelDtlDTO.setPosition(referencePersonnelDtl.getPosition());
            referencePersonnelDtlDTO.setOfficeAddress(referencePersonnelDtl.getOfficeAddress());
            referencePersonnelDtlDTO.setOfficeDetailAddress(referencePersonnelDtl.getOfficeDetailAddress());
            referencePersonnelDtlDTO.setOfficeTel(referencePersonnelDtl.getOfficeTel());
            referencePersonnelDtlDTO.setOfficeFax(referencePersonnelDtl.getOfficeFax());
            referencePersonnelDtlDTO.setCompanyWebsiteUrl(referencePersonnelDtl.getCompanyWebsiteUrl());
            referencePersonnelDtlDTO.setNote(referencePersonnelDtl.getNote());
            referencePersonnelDtlDTO.setCreator(referencePersonnelDtl.getCreator());
            referencePersonnelDtlDTO.setCreateDate(referencePersonnelDtl.getCreateDate());
        /*  private Integer id;
            private ReferencePersonnelM personnel;*/
        }
        return referencePersonnelDtlDTO;
    }

    // 상위 처리과정 개선을 포함하고 있으며 현재 update 화면에서 form 내용 처리용
    // presonnelDTO 중심으로 개선
    public ReferencePersonnelDTO getReferencePersonnelDTO(Integer id){
        ReferencePersonnelM referencePersonnelM = this.referencePersonnelMService.getReferencePersonnelM(id);
        ReferencePersonnelDtl referencePersonnelDtl = this.referencePersonnelDtlService.getReferencePersonnelDtlByPersonnel(referencePersonnelM);
        // 관련자 1명의 Integer id 값을 이용하여 상세정보를 가지고 온다.
        ReferencePersonnelDTO referencePersonnelDTO = new ReferencePersonnelDTO();

        if (referencePersonnelDTO != null) {
            referencePersonnelDTO.setPersonnel(referencePersonnelDtl.getPersonnel().getId());
            referencePersonnelDTO.setId(referencePersonnelDtl.getId());
            referencePersonnelDTO.setCompanyName(referencePersonnelDtl.getCompanyName());
            referencePersonnelDTO.setDeptName(referencePersonnelDtl.getDeptName());
            referencePersonnelDTO.setPosition(referencePersonnelDtl.getPosition());
            referencePersonnelDTO.setOfficeAddress(referencePersonnelDtl.getOfficeAddress());
            referencePersonnelDTO.setOfficeDetailAddress(referencePersonnelDtl.getOfficeDetailAddress());
            referencePersonnelDTO.setOfficeTel(referencePersonnelDtl.getOfficeTel());
            referencePersonnelDTO.setOfficeFax(referencePersonnelDtl.getOfficeFax());
            referencePersonnelDTO.setCompanyWebsiteUrl(referencePersonnelDtl.getCompanyWebsiteUrl());
            referencePersonnelDTO.setNote(referencePersonnelDtl.getNote());
            referencePersonnelDTO.setCreator(referencePersonnelDtl.getCreator());
            referencePersonnelDTO.setCreateDate(referencePersonnelDtl.getCreateDate());
            referencePersonnelDTO.setFirstName(referencePersonnelM.getFirstName());
            referencePersonnelDTO.setLastName(referencePersonnelM.getLastName());
            referencePersonnelDTO.setMiddleName(referencePersonnelM.getMiddleName());
            referencePersonnelDTO.setNationality(referencePersonnelM.getNationality());
            referencePersonnelDTO.setEmail(referencePersonnelM.getEmail());
            referencePersonnelDTO.setCellPhone(referencePersonnelM.getCellPhone());
        /*  private Integer id;
            private ReferencePersonnelM personnel;*/
        }
        return referencePersonnelDTO;
    }

    // 기존 등록된 내용의 수정결과 저장 시작
    @Transactional
    public Integer updatePersonnel(ReferencePersonnelDTO dto){
        //ReferencePersonnelM m = new ReferencePersonnelM();
        ReferencePersonnelM m = referencePersonnelMService.getReferencePersonnelM(dto.getId());
        if (m == null) {
            throw new EntityNotFoundException("지원인력 기본정보를 찾을 수 없습니다. ID: " + dto.getId());
        }

        m.setFirstName(dto.getFirstName());
        m.setLastName(dto.getLastName());
        m.setMiddleName(dto.getMiddleName());
        m.setNationality(dto.getNationality());
        m.setEmail(dto.getEmail());
        m.setCellPhone(dto.getCellPhone());
        m.setCreator(dto.getCreator());
        m.setCreateDate(LocalDateTime.now());

        // ReferencePersonnelM savedM = referencePersonnelMService.saveReferencePersonnelM(m);
        // Integer generatedId = savedM.getId();
        // ReferencePersonnelDtl dtl = new ReferencePersonnelDtl();

        // ReferencePersonnelDtl dtl = referencePersonnelDtlService.getReferencePersonnelDtlByPersonnel(dto.getId());
        ReferencePersonnelDtl dtl = referencePersonnelDtlService.getReferencePersonnelDtlByPersonnel(m);
        if (dtl == null) {
            dtl = new ReferencePersonnelDtl();
            dtl.setPersonnel(m); // FK 연결
            dtl.setCreateDate(LocalDateTime.now()); // 새로 생성된 경우
        }


        dtl.setCompanyName(dto.getCompanyName());
        dtl.setDeptName(dto.getDeptName());
        dtl.setOfficeAddress(dto.getOfficeAddress());
        dtl.setOfficeDetailAddress(dto.getOfficeDetailAddress());
        dtl.setOfficeTel(dto.getOfficeTel());
        dtl.setOfficeFax(dto.getOfficeFax());
        dtl.setCompanyWebsiteUrl(dto.getCompanyWebsiteUrl());
        dtl.setNote(dto.getNote());
        dtl.setCreateDate(dto.getCreateDate());
        dtl.setPersonnel(m); // FK 관계 설정
        dtl.setCreator(dto.getCreator());

        // dtl.setPersonnel(savedM); // FK 연결
        referencePersonnelMService.saveReferencePersonnelDtl(dtl);
        return m.getId();
    }

    @Transactional
    public Integer saveReferencePersonnel(ReferencePersonnelDTO dto) {
        ReferencePersonnelM m = new ReferencePersonnelM();
        m.setFirstName(dto.getFirstName());
        m.setLastName(dto.getLastName());
        m.setMiddleName(dto.getMiddleName());
        m.setNationality(dto.getNationality());
        m.setEmail(dto.getEmail());
        m.setCellPhone(dto.getCellPhone());
        // m.setCreateDate(dto.getCreateDate()); 자동
        // login 이 아직 구현되지 않아 Creator 에 "sys_admin"을 임시저장
        // m.setCreator("sys_admin");
        m.setCreator(dto.getCreator());
        //String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        //m.setCreator(currentUsername);

        // referencePersonnelMService.saveReferencePersonnelM(m); // 저장
        // 저장후 id @GeneratedValue(strategy = GenerationType.IDENTITY) 값을 반환하기 위한 저장된 객체 관리
        // 제일 마지막 줄에 retrun 선언 ??
        ReferencePersonnelM savedM = referencePersonnelMService.saveReferencePersonnelM(m);
        Integer generatedId = savedM.getId();

        ReferencePersonnelDtl dtl = new ReferencePersonnelDtl();
        dtl.setCompanyName(dto.getCompanyName());
        dtl.setCompanyName(dto.getPosition());
        dtl.setDeptName(dto.getDeptName());
        dtl.setOfficeAddress(dto.getOfficeAddress());
        dtl.setOfficeDetailAddress(dto.getOfficeDetailAddress());
        dtl.setOfficeTel(dto.getOfficeTel());
        dtl.setOfficeFax(dto.getOfficeFax());
        dtl.setCompanyWebsiteUrl(dto.getCompanyWebsiteUrl());
        dtl.setNote(dto.getNote());
        dtl.setCreateDate(dto.getCreateDate());
        dtl.setPersonnel(m); // FK 관계 설정
        // dtl.setCreator("sys_admin"); // 로그인 기능 미비에 따른 임시
        dtl.setCreator(dto.getCreator());
        //dtl.setCreator(currentUsername); // 로그인 이후 
        // referencePersonnelMService.saveReferencePersonnelDtl(dtl); // 저장

        dtl.setPersonnel(savedM); // FK 연결
        referencePersonnelMService.saveReferencePersonnelDtl(dtl);
        return generatedId;

/*
        // 에러처리 롬복 추가 후 컴파일 오류로 보류함. DEPENDENCIES 에서도 모두 주석처리된 상태임
        try {
            // 저장 로직

        } catch (Exception e) {
            log.error("Unexpected error occured.", e);
            throw new ReferencePersonnelSaveException("Reference Personnel is not registrated.", e);
        }*/
    }

    // 페이징
    private final ReferencePersonnelMRepository referencePersonnelMRepository;
    public Page<ReferencePersonnelDTO> getPagedReferencePersonnel(Pageable pageable) {
        Page<ReferencePersonnelM> page = referencePersonnelMRepository.findAll(pageable);
        return page.map(this::convertToDTO); // DTO 변환 로직 포함
    }

    private ReferencePersonnelDTO convertToDTO(ReferencePersonnelM m) {
        ReferencePersonnelDTO dto = new ReferencePersonnelDTO();
        dto.setId(m.getId());
        dto.setNationality(m.getNationality());
        dto.setFirstName(m.getFirstName());
        dto.setLastName(m.getLastName());
        dto.setMiddleName(m.getMiddleName());
        dto.setEmail(m.getEmail());
        dto.setCellPhone(m.getCellPhone());
        dto.setCreator(m.getCreator());
        dto.setCreateDate(m.getCreateDate());

        ReferencePersonnelDtl dtl = referencePersonnelDtlService.getReferencePersonnelDtlByPersonnel(m);
        if (dtl != null) {
            dto.setCompanyName(dtl.getCompanyName());
        }
        return dto;
    }

/*
    // 삭제는 엔터티 레파지토리를 활용
    private final ReferencePersonnelMRepository referencePersonnelMRepository;

    @Transactional
    public void deleteDetailOnly(Integer masterId) {
        ReferencePersonnelM master = referencePersonnelMRepository.findById(masterId)
                .orElseThrow(() -> new IllegalArgumentException("해당 마스터가 존재하지 않습니다: " + masterId));

        master.setDetail(null); // 관계 끊기
        referencePersonnelMRepository.save(master); // 변경 감지 → orphanRemoval 작동
    }
*/
}