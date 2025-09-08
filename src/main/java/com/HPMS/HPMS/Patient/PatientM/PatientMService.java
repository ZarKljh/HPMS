package com.HPMS.HPMS.Patient.PatientM;

import com.HPMS.HPMS.Patient.PatientDTL.PatientDTL;
import com.HPMS.HPMS.Patient.PatientDTL.PatientDTLService;
import com.HPMS.HPMS.Patient.patientForm.PatientForm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.core.annotation.MergedAnnotations.search;

@Service
@RequiredArgsConstructor
public class PatientMService {
    private final PatientMRepository patientMRepository;
    private final PatientDTLService patientDTLService;
    private final EntityManager entityManager;

    //모든 환자의 Main정보를 가져온다
    public List<PatientM> getAllPatientM(){
        return this.patientMRepository.findAll();
    }

    public Page<PatientM> getAllPatientM(Pageable pageable) {
        return this.patientMRepository.findAll(pageable);
    }

    //다중조건에 따른 환자의 Main정보를 가져온다
    public Page<PatientM>  patientMSearch( List<String> columns,
                                           List<String> operators,
                                           List<String> values,
                                           List<String> logicalOperators,
                                           Pageable pageable){



//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<PatientM> query = cb.createQuery(PatientM.class);
//        Root<PatientM> root = query.from(PatientM.class);

        // Predicate 생성
//        List<Predicate> predicates = buildPredicates(cb, root, columns, operators, values);
        Specification<PatientM> spec = search(columns, operators, values, logicalOperators);
        return this.patientMRepository.findAll(spec, pageable);
    }

    private Specification<PatientM> search(
            List<String> columns,
            List<String> operators,
            List<String> values,
            List<String> logicalOperators
    ) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<PatientM> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true); // 중복 제거

                if (columns == null || columns.isEmpty()) {
                    return cb.conjunction(); // 조건이 없으면 전체 검색
                }

                // 첫 번째 조건을 기본으로 설정
                Predicate combinedPredicate = buildPredicate(cb, root, columns.get(0), operators.get(0), values.get(0));

                // 나머지 조건은 logicalOperators로 연결
                for (int i = 1; i < columns.size(); i++) {
                    Predicate nextPredicate = buildPredicate(cb, root, columns.get(i), operators.get(i), values.get(i));
                    String logicalOp = (i - 1 < logicalOperators.size()) ? logicalOperators.get(i - 1) : "AND";

                    if ("OR".equalsIgnoreCase(logicalOp)) {
                        combinedPredicate = cb.or(combinedPredicate, nextPredicate);
                    } else {
                        combinedPredicate = cb.and(combinedPredicate, nextPredicate);
                    }
                }

                return combinedPredicate;
            }
        };
    }

    private Predicate buildPredicate(
            CriteriaBuilder cb,
            Root<PatientM> root,
            String column,
            String operator,
            String value
    ) {
        if (column == null || column.isBlank() || value == null || value.isBlank()) {
            return cb.conjunction(); // 빈 값이면 true
        }

        Path<Object> path = root.get(column);

        return switch (operator) {
            case "=" -> {
                if ("birth".equals(column)) {
                    yield cb.equal(path.as(Integer.class), Integer.valueOf(value));
                } else if ("createDate".equals(column) || "lastVisitDate".equals(column)) {
                    yield cb.equal(path.as(LocalDateTime.class), LocalDate.parse(value).atStartOfDay());
                } else {
                    yield cb.equal(path, value);
                }
            }
            case "like" -> cb.like(path.as(String.class), "%" + value + "%");
            case ">" -> {
                if ("birth".equals(column)) {
                    yield cb.greaterThan(path.as(Integer.class), Integer.valueOf(value));
                } else if ("createDate".equals(column) || "lastVisitDate".equals(column)) {
                    yield cb.greaterThan(path.as(LocalDateTime.class), LocalDate.parse(value).atStartOfDay());
                } else {
                    yield cb.greaterThan(path.as(String.class), value);
                }
            }
            case "<" -> {
                if ("birth".equals(column)) {
                    yield cb.lessThan(path.as(Integer.class), Integer.valueOf(value));
                } else if ("createDate".equals(column) || "lastVisitDate".equals(column)) {
                    yield cb.lessThan(path.as(LocalDateTime.class), LocalDate.parse(value).atStartOfDay());
                } else {
                    yield cb.lessThan(path.as(String.class), value);
                }
            }
            default -> cb.conjunction(); // 알 수 없는 연산자는 true 반환
        };
    }

    // 환자1명의 Main정보를 가져온다
    public PatientM getPatientM(Integer id){
        Optional<PatientM> patientM = this.patientMRepository.findById(id);
        if (patientM.isPresent()) {
            return patientM.get();
        } else {
            throw new NoSuchElementException("Patient detail not found for patientId: " + patientM.get().getId());
            //throw new DataNotFoundException("question not found");
        }
    }

    public void deletePatientM(PatientM patientM){
        //환자종결구분자에 1을 입력한다
        patientM.setDelStatus(1);
        this.patientMRepository.save(patientM);
    }

    public void createPatientM(PatientForm pf){
        PatientM m = new PatientM();
        PatientDTL dtl = new PatientDTL();

        m.setFirstName(pf.getFirstName());
        m.setLastName(pf.getLastName());
        m.setMiddleName(pf.getMiddleName());
        m.setPassFirstName(pf.getPassFirstName());
        m.setPassLastName(pf.getLastName());
        m.setPassMiddleName(pf.getPassMiddleName());
        m.setGender(pf.getGender());
        m.setDayOfBirth(localDateToInteger(pf.getBirth()));
        m.setForeigner(pf.getForeigner());
        m.setPassport(pf.getPassport());
        m.setCreateDate(LocalDateTime.now());
        m.setUpdateDate(LocalDateTime.now());
        m.setDelStatus(0);

        this.patientMRepository.save(m);

        dtl.setPatientM(m);
        dtl.setMobilePhone(removeHyphen(pf.getMobilePhone()));
        dtl.setHomePhone(removeHyphen(pf.getHomePhone()));
        dtl.setFax(removeHyphen(pf.getFax()));
        dtl.setEmail(pf.getEmail());

        dtl.setGuardianFirstName(pf.getGuardianFirstName());
        dtl.setGuardianLastName(pf.getGuardianLastName());
        dtl.setGuardianMiddleName(pf.getGuardianMiddleName());
        dtl.setGuardianTel(removeHyphen(pf.getGuardianTel()));
        dtl.setGuardianRelation(pf.getGuardianRelation());

        dtl.setCurHomePCD(pf.getHomePcd());
        dtl.setCurHomeDefAdd(pf.getHomeDefAdd());
        dtl.setCurHomeDetAdd(pf.getHomeDetAdd());
        dtl.setRegHomePCD(pf.getRegPcd());
        dtl.setRegHomeDefAdd(pf.getRegDefAdd());
        dtl.setRegHomeDetAdd(pf.getRegDefAdd());

        dtl.setOccupation(pf.getOccupation());
        dtl.setLastVisitDate(LocalDateTime.now());

        dtl.setNatn(pf.getNatn());
        dtl.setNote(pf.getNote());

        this.patientDTLService.createPatientDTL(dtl);
    }

    public Integer modifyPatientM(PatientM m, PatientForm pf){
        //PatientM과 연결되어있는 PatientDTL 객체를 가져온다
        PatientDTL dtl = this.patientDTLService.getPatientDTLByPatientId(m);
        //정보수정을 위핸 PatientM 과 PatientDTL이 준비되었다

        //PatientForm에 들어있는 정보들을 PatientM 과 PatientDTL에 셋팅한다

        m.setFirstName(pf.getFirstName());
        m.setLastName(pf.getLastName());
        m.setMiddleName(pf.getMiddleName());
        m.setPassFirstName(pf.getPassFirstName());
        m.setPassLastName(pf.getLastName());
        m.setPassMiddleName(pf.getPassMiddleName());
        m.setGender(pf.getGender());
        m.setDayOfBirth(localDateToInteger(pf.getBirth()));
        m.setForeigner(pf.getForeigner());
        m.setPassport(pf.getPassport());
        //생성날짜는 변경할수 없다
        //m.setCreateDate(LocalDateTime.now());
        m.setUpdateDate(LocalDateTime.now());
        //환자삭제는 삭제기능에서 이루어진다
        //m.setDelStatus(0);

        this.patientMRepository.save(m);

        dtl.setPatientM(m);
        dtl.setMobilePhone(removeHyphen(pf.getMobilePhone()));
        dtl.setHomePhone(removeHyphen(pf.getHomePhone()));
        dtl.setOfficePhone(removeHyphen(pf.getOfficePhone()));
        dtl.setFax(removeHyphen(pf.getFax()));
        dtl.setEmail(pf.getEmail());

        dtl.setGuardianFirstName(pf.getGuardianFirstName());
        dtl.setGuardianLastName(pf.getGuardianLastName());
        dtl.setGuardianMiddleName(pf.getGuardianMiddleName());
        dtl.setGuardianTel(removeHyphen(pf.getGuardianTel()));
        dtl.setGuardianRelation(pf.getGuardianRelation());

        dtl.setCurHomePCD(pf.getHomePcd());
        dtl.setCurHomeDefAdd(pf.getHomeDefAdd());
        dtl.setCurHomeDetAdd(pf.getHomeDetAdd());
        dtl.setRegHomePCD(pf.getRegPcd());
        dtl.setRegHomeDefAdd(pf.getRegDefAdd());
        dtl.setRegHomeDetAdd(pf.getRegDefAdd());

        dtl.setOccupation(pf.getOccupation());
        dtl.setLastVisitDate(LocalDateTime.now());

        dtl.setNatn(pf.getNatn());
        dtl.setNote(pf.getNote());

        this.patientDTLService.modifyPatientDTL(dtl);

        return m.getId();
    }

    private List<Predicate> buildPredicates(
            CriteriaBuilder cb,
            Root<PatientM> root,
            List<String> columns,
            List<String> operators,
            List<String> values
    ) {
        List<Predicate> predicates = new ArrayList<>();

        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            String operator = operators.get(i);
            String value = values.get(i);

            Path<Object> path = root.get(column);
            Predicate predicate = null;

            switch (operator) {
                case "=" -> predicate = buildEqualPredicate(cb, path, column, value);
                case "like" -> predicate = cb.like(path.as(String.class), "%" + value + "%");
                case ">" -> predicate = buildComparisonPredicate(cb, path, column, value, true);
                case "<" -> predicate = buildComparisonPredicate(cb, path, column, value, false);
            }

            if (predicate != null) predicates.add(predicate);
        }

        return predicates;
    }

    private Predicate buildEqualPredicate(CriteriaBuilder cb, Path<Object> path, String column, String value) {
        try {
            return switch (column) {
                case "birth" -> cb.equal(path.as(Integer.class), Integer.valueOf(value));
                case "createDate", "lastVisitDate" ->
                        cb.equal(path.as(LocalDateTime.class), LocalDateTime.parse(value));
                default -> cb.equal(path, value);
            };
        } catch (Exception e) {
            throw new IllegalArgumentException("잘못된 값: " + value, e);
        }
    }

    private Predicate buildComparisonPredicate(
            CriteriaBuilder cb, Path<Object> path, String column, String value, boolean isGreater) {
        try {
            if ("birth".equals(column)) {
                return isGreater
                        ? cb.greaterThan(path.as(Integer.class), Integer.valueOf(value))
                        : cb.lessThan(path.as(Integer.class), Integer.valueOf(value));
            } else if ("createDate".equals(column) || "lastVisitDate".equals(column)) {
                LocalDateTime dateTimeValue = LocalDateTime.parse(value);
                return isGreater
                        ? cb.greaterThan(path.as(LocalDateTime.class), dateTimeValue)
                        : cb.lessThan(path.as(LocalDateTime.class), dateTimeValue);
            } else {
                return isGreater
                        ? cb.greaterThan(path.as(String.class), value)
                        : cb.lessThan(path.as(String.class), value);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("잘못된 값: " + value, e);
        }
    }

    // LocalDate를 Integer YYYYMMDD 형태로 변환
    public Integer localDateToInteger(LocalDate date) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateString = date.format(formatter);
        return Integer.valueOf(dateString);
    }
    public String removeHyphen(String phone) {
        if (phone == null) {
            return null; // null 체크
        }
        return phone.replace("-", "");
    }
}

