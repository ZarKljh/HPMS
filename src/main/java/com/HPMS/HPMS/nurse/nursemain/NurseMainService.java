package com.HPMS.HPMS.nurse.nursemain;

import com.HPMS.HPMS.nurse.NurseDataNotFoundException;
import com.HPMS.HPMS.nurse.nurseinformation.NurseInformation;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NurseMainService {

    @Autowired
    private NurseMainRepository nurseMainRepository;

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

    public NurseMain getById(Integer id) {
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

    public List<NurseMain> getNurseMainByName(String firstName, String lastName){
        return this.nurseMainRepository.findByFirstNameAndLastName(firstName, lastName);

    private Specification<NurseMain> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<NurseMain> m, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<NurseMain, NurseInformation> i = m.join("nurseInformation", JoinType.LEFT);
                return cb.or(cb.like(m.get("firstName"), "%" + kw + "%"), // 이름
                        cb.like(m.get("lastName"), "%" + kw + "%"),      // 성
                        cb.like(m.get("dept"), "%" + kw + "%"),    // 부서
                        cb.like(m.get("rank"), "%" + kw + "%"),      // 직급
                        cb.like(m.get("sts"), "%" + kw + "%"),      // 상태
                        cb.like(m.get("wt"), "%" + kw + "%"),      // 근무형태
                        cb.like(m.get("writer"), "%" + kw + "%"),      // 작성자
                        cb.like(i.get("tel"), "%" + kw + "%"),      // 전화번호
                        cb.like(m.get("modifier"), "%" + kw + "%"));   // 수정자
            }
        };
    }
}
