package com.HPMS.HPMS.nurse.nursemain;

import com.HPMS.HPMS.nurse.NurseDataNotFoundException;
import com.HPMS.HPMS.nurse.nurseinformation.NurseInformation;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NurseMainService {

    private final NurseMainRepository nurseMainRepository;

    public List<NurseMain> getAll() {
        return nurseMainRepository.findAll();
    }

    public NurseMain getNurseMain(Integer id) {
        Optional<NurseMain> nurseMain = this.nurseMainRepository.findById(id);
        if (nurseMain.isPresent()) {
            return nurseMain.get();
        } else {
            throw new NurseDataNotFoundException("nurse not found");
        }
    }

    public NurseMain findById(Integer id) {
        return nurseMainRepository.findById(id).orElse(null);
    }

    public Page<NurseMain> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 15, Sort.by(sorts));
        Specification<NurseMain> spec = search(kw);
        return this.nurseMainRepository.findAll(spec, pageable);
    }

    public NurseMain save(NurseMain nurseMain) {
        return nurseMainRepository.save(nurseMain);
    }

    public List<NurseMain> getNurseMainByName(String firstName, String lastName) {
        return this.nurseMainRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Page<NurseMain> getList(int page, int size, String kw) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        if (kw == null || kw.isEmpty()) {
            return nurseMainRepository.findAll(pageable);
        } else {
            return nurseMainRepository.findByFirstNameContaining(kw, pageable);
        }
    }

    private Specification<NurseMain> search(String kw) {
        return (Root<NurseMain> m, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            query.distinct(true); // 중복 제거
            Join<NurseMain, NurseInformation> i = m.join("nurseInformation", JoinType.LEFT);

            // 공백 기준으로 키워드 분리
            String[] keywords = kw.trim().split("\\s+");
            List<Predicate> andPredicates = new ArrayList<>();

            for (String keyword : keywords) {
                String pattern = "%" + keyword + "%";

                // 각 키워드가 걸릴 수 있는 컬럼들 (OR 조건)
                Predicate orPredicate = cb.or(
                        cb.like(m.get("firstName"), pattern),   // 이름
                        cb.like(m.get("lastName"), pattern),    // 성
                        cb.like(m.get("dept"), pattern),        // 부서
                        cb.like(m.get("rank"), pattern),        // 직급
                        cb.like(m.get("sts"), pattern),         // 상태
                        cb.like(m.get("wt"), pattern),          // 근무형태
                        cb.like(m.get("writer"), pattern),      // 작성자
                        cb.like(i.get("tel"), pattern),         // 전화번호
                        cb.like(m.get("modifier"), pattern)     // 수정자
                );

                // 여러 키워드는 AND로 묶음
                andPredicates.add(orPredicate);
            }
            return cb.and(andPredicates.toArray(new Predicate[0]));
        };
    }

    // 다중 삭제 메서드
    @Transactional
    public void deleteMultipleNurses(List<Integer> nurseIds) {
        System.out.println("Service: deleteMultipleNurses 호출됨");
        System.out.println("Service: 받은 ID 리스트: " + nurseIds);

        if (nurseIds == null || nurseIds.isEmpty()) {
            System.out.println("Service: ID 리스트가 비어있음");
            throw new IllegalArgumentException("삭제할 간호사 ID가 없습니다.");
        }

        // 1. JPA 관리 엔티티 조회
        List<NurseMain> nursesToDelete = nurseMainRepository.findAllById(nurseIds);
        System.out.println("Service: 찾은 간호사 수: " + nursesToDelete.size());

        if (nursesToDelete.isEmpty()) {
            System.out.println("Service: 삭제할 간호사 정보를 찾을 수 없음");
            throw new RuntimeException("삭제할 간호사 정보를 찾을 수 없습니다.");
        }

        for (NurseMain nurse : nursesToDelete) {
            System.out.println("Service: 삭제될 간호사 - ID: " + nurse.getId() + ", 이름: " + nurse.getFirstName());
        }

        // 2. Cascade + orphanRemoval로 안전하게 삭제
        for (NurseMain nurse : nursesToDelete) {
            nurseMainRepository.delete(nurse);
        }

        System.out.println("Service: 삭제 완료");
    }

}
