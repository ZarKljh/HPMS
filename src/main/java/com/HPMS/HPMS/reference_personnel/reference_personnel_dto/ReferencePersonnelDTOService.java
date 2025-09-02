package com.HPMS.HPMS.reference_personnel.reference_personnel_dto;

import com.HPMS.HPMS.reference_personnel.reference_personnel_dtl.ReferencePersonnelDtl;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dtl.ReferencePersonnelDtlService;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dto.personnel_dtl_dto.ReferencePersonnelDtlDTO;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dto.personnel_m_dto.ReferencePersonnelMDTO;
import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelM;
import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelMService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReferencePersonnelDTOService {
    // 메인 엔티티 목록을 가져오는 서비스
    private final ReferencePersonnelMService referencePersonnelMService;
    // 상세 정보(DTL)를 가져오는 서비스
    private final ReferencePersonnelDtlService referencePersonnelDtlService;

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
}