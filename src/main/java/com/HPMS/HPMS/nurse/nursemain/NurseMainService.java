package com.HPMS.HPMS.nurse.nursemain;

import com.HPMS.HPMS.nurse.NurseDataNotFoundException;
import jakarta.persistence.criteria.Predicate;
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
            // 검색어 있으면 Specification 사용
            Specification<NurseMain> spec = search(kw);
            return nurseMainRepository.findAll(spec, pageable);
        }
    }

    private Specification<NurseMain> search(String kw) {
        return (root, query, cb) -> {
            query.distinct(true);

            String pattern = "%" + kw + "%"; // 한글/영어 그대로 사용
            ArrayList<Predicate> orList = new ArrayList<>();

            orList.add(cb.like(cb.coalesce(root.get("firstName"), ""), pattern));
            orList.add(cb.like(cb.coalesce(root.get("lastName"), ""), pattern));
            orList.add(cb.like(cb.coalesce(root.get("dept"), ""), pattern));
            orList.add(cb.like(cb.coalesce(root.get("rank"), ""), pattern));
            orList.add(cb.like(cb.coalesce(root.get("sts"), ""), pattern));
            orList.add(cb.like(cb.coalesce(root.get("wt"), ""), pattern));
            orList.add(cb.like(cb.coalesce(root.get("writer"), ""), pattern));
            orList.add(cb.like(cb.coalesce(root.get("modifier"), ""), pattern));

            return cb.or(orList.toArray(new Predicate[0]));
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
