package com.HPMS.HPMS.reference_personnel;

import com.HPMS.HPMS.reference_personnel.reference_personnel_dtl.ReferencePersonnelDtlRepository;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dtl.ReferencePersonnelDtlService;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dto.ReferencePersonnelDTOService;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dto.personnel_dtl_dto.ReferencePersonnelDtlDTO;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dto.personnel_m_dto.ReferencePersonnelMDTO;
import com.HPMS.HPMS.reference_personnel.reference_personnel_dto.ReferencePersonnelDTO;
import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelM;
import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelMRepository;
import com.HPMS.HPMS.reference_personnel.reference_personnel_m.ReferencePersonnelMService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
public class ReferencePersonnelController {
    private final ReferencePersonnelMRepository referencePersonnelMRepository;
    private final ReferencePersonnelDTOService referencePersonnelDTOService;
    private final ReferencePersonnelDtlService referencePersonnelDtlService;

/*
    // 아래 내용으로 대체
    @GetMapping("/user/list")
    public String referencePersonnelList(Model model){
        List<ReferencePersonnelMDTO> referencePersonnels = this.referencePersonnelDTOService.getReferencePersonelMDTO();
        model.addAttribute("referencePersonnels", referencePersonnels);
        return "personnel/personnel_list";
    }
*/

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM')")
    @GetMapping("/user/list")
    public String referencePersonnelList(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         Model model) {
        // personnel 순번처리
        int internalPage = page - 1; // 내부 논리처리는 0부터 시작
        PageRequest pageRequest = PageRequest.of(internalPage, size, Sort.by("createDate").ascending());
        Page<ReferencePersonnelDTO> pagedList = referencePersonnelDTOService.getPagedReferencePersonnel(pageRequest);

        List<ReferencePersonnelDTO> content = pagedList.getContent();
        int totalElements = (int) pagedList.getTotalElements();

        for (int i = 0; i < content.size(); i++) {
            ReferencePersonnelDTO dto = content.get(i);
            int currentNum = internalPage * size + i + 1; // 전체 기준 순번
            dto.setCurrentNum(currentNum);      // DTO 에 선언된 값을 참조
            dto.setTotalNum((int) pagedList.getTotalElements());
        }

        // 페이징 네비게이션
        // int internalPage = page - 1; // 내부는 0부터 시작
        //PageRequest pageRequest = PageRequest.of(internalPage, size, Sort.by("id").ascending());
        //Page<ReferencePersonnelDTO> pagedList = referencePersonnelDTOService.getPagedReferencePersonnel(pageRequest);

        int totalPages = pagedList.getTotalPages();
        if (internalPage >= totalPages) {
            internalPage = 0;
            page = 1;
        }
        int startPage = Math.max(page - 2, 1);
        int endPage = Math.min(page + 2, totalPages);

        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", page); // 외부용 1-based
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("referencePersonnels", pagedList.getContent());
        model.addAttribute("size", size);
        model.addAttribute("personnelPage", pagedList);
        // model.addAttribute("currentNum", currentNum); //DTO에 포함시켜 놓았기 때문에 불필요
        model.addAttribute("totalNum", totalElements); //DTO에 포함시켜 놓았기 때문에 불필요하지만 화면 출력편의를 위해

        return "personnel/personnel_list";
    }
    // AJAX 기반 UPDATE
/*    @PostMapping("/user/list-fragment")
    public String getPersonnelListFragment(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           Model model) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<ReferencePersonnelDTO> pagedList = referencePersonnelDTOService.getPagedReferencePersonnel(pageRequest);

        int totalPages = pagedList.getTotalPages();
        if (page >= totalPages) {
            page = 0;
        }
        int currentPage = page;

        int startPage = Math.max(currentPage - 2, 0);
        int endPage = Math.min(currentPage + 2, totalPages - 1);

        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("referencePersonnels", pagedList.getContent());
        model.addAttribute("size", size);
        model.addAttribute("personnelPage", pagedList);

        return "personnel/personnel_list :: personnelList"; // fragment 이름
    }*/
/*    @GetMapping("/user/detail/{id}/{size}/{page}")
    public String referencePersonnelDtl(Model model,
                                        @PathVariable("id") Integer id,
                                        @PathVariable("size") Integer size,
                                        @PathVariable("page") Integer page){
        System.out.println("id is " + id);
        System.out.println("size is " + size);
        System.out.println("page is " + page);
        // PatientDetailDTO detailDTO = this.patientDTOService.getPatientDetailDTO(id);
        ReferencePersonnelDtlDTO referencePersonnelDtlDTO = this.referencePersonnelDTOService.getReferencePersonnelDtlDTO(id);
        model.addAttribute("referencePersonnelDtlDTO",referencePersonnelDtlDTO);
        return "personnel/personnel_detail/"+id+"?page="+page+"&size="+size;
    }*/

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM')")
    @GetMapping("/user/detail/{id}")
    public String referencePersonnelDtl(Model model,
                                        @PathVariable("id") Integer id,
                                        @RequestParam("size") Integer size,
                                        @RequestParam("page") Integer page){
        // 경로 변수 (@PathVariable): 리소스 식별에 직접적으로 관련된 값일 때 사용 (예: id)
        // 쿼리 파라미터 (@RequestParam): 필터링, 페이징, 정렬 등 부가적인 정보일 때 사용 (예: size, page)
        System.out.println("personnel_detail id is " + id);
        System.out.println("personnel_detail size is " + size);
        System.out.println("personnel_detail page is " + page);
        ReferencePersonnelDTO personnel = this.referencePersonnelDTOService.getReferencePersonnelDTO(id);
        model.addAttribute("personnel", personnel);
        // view 에서
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "personnel/personnel_detail";
    }

    // 상세 화면에서 1건 삭제
    private final ReferencePersonnelMService referencePersonnelMService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM')")
    @PostMapping("/user/delete")
    public String deleteReferencePersonnel(RedirectAttributes redirectAttributes,
                                           @RequestParam("id") Integer id,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int size) {

        try {
            referencePersonnelMService.deletePersonnel(id);
            redirectAttributes.addFlashAttribute("successMessage", "삭제가 완료되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/user/list?page=" + page + "&size=" + size;
    }

    // Start of personnel 목록에서 체크박스 다중 선택 후 삭제하는 기능
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM')")
    @PostMapping("/delete/selectedPersonnel")
    public String deleteSelectedPersonnel(RedirectAttributes redirectAttributes,
                                          @RequestParam(value = "checkedIds", required = true) List<Long> ids,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int size){
        /*
        1. 컨트롤러에서 수신된 ID 수신(requestParam) 및 검증(is null?)
        2. 서비스 계층에서 삭제 로직 처리
        3. Repository에서 실제 삭제 수행
        4. 삭제 후 리다이렉트
        * */
        if (ids == null || ids.isEmpty()) {
            // return messages
            return "redirect:/user/list?page=" + page + "&size=" + size;
        }

        referencePersonnelMService.deleteByIds(ids);
        return "redirect:/user/list?page=" + page + "&size=" + size;
    }

    /*업데이트 화면 전용
    @GetMapping("/user/update/{id}")  //update 화면 호출
    public String referencePersonnel(Model model, @PathVariable("id") Integer id){
        ReferencePersonnelDTO referencePersonnelDTO = this.referencePersonnelDTOService.getReferencePersonnelDTO(id);
        model.addAttribute("referencePersonnelDTO",referencePersonnelDTO);
        return "personnel/personnel_update";
    }
    */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM')")
    @GetMapping("/user/personnel_update")  //update 화면 채우기
    public String referencePersonnel(@RequestParam Integer id, @RequestParam Integer page, @RequestParam Integer size, Model model) {
        ReferencePersonnelDTO personnel = this.referencePersonnelDTOService.getReferencePersonnelDTO(id);
        model.addAttribute("personnel",personnel);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "personnel/personnel_update";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM')")
    @PostMapping("/update/personnel")    // update 화면에서 값을 받아 저장한 후 상세보기 화면으로 이동하기
    public String updatePersonnel(@ModelAttribute ReferencePersonnelDTO dto,
                                  @RequestParam Integer page,
                                  @RequestParam Integer size,
                                  RedirectAttributes redirectAttributes,
                                  Principal principal) {
        String username = (principal != null) ? principal.getName() : "sysadmin";   // 로그인처리가 안된 경우
        dto.setCreator(username);
        referencePersonnelDTOService.updatePersonnel(dto);

        redirectAttributes.addAttribute("id", dto.getId());
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("size", size);

        return "redirect:/user/detail/" + dto.getId() + "?page=" + page + "&size=" + size; // 기존 상세화면에 맞추어
        // return "redirect:/user/personnel_update";
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM')")
    @GetMapping("/user/personnel_registration")
    public String personnelRegisration() {
        return "personnel/personnel_registration";
    }


    //  신규 관련자 등록 시작
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SYSTEM')")
    @PostMapping("/create/reference_personal")
    public String createReferencePersonnel(@ModelAttribute ReferencePersonnelDTO dto, RedirectAttributes redirectAttributes,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           Principal principal) {
        String username = (principal != null) ? principal.getName() : "sysadmin";   // 로그인처리가 안된 경우
        dto.setCreator(username); // 주입 성공
        try {
            // referencePersonnelDTOService.saveReferencePersonnel(dto);
            Integer id = referencePersonnelDTOService.saveReferencePersonnel(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Registration Result.");
            return "redirect:/user/detail/" + id + "?page=" + page + "&size=" + size;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "저장 중 오류가 발생했습니다.");
            return "redirect:/user/personnel_registration?page=" + page + "&size=" + size;
        }
        //  신규 관련자 등록 끝
    }

}
