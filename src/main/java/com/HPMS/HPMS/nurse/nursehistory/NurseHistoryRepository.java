package com.HPMS.HPMS.nurse.nursehistory;

import com.HPMS.HPMS.nurse.nursemain.NurseMain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NurseHistoryRepository extends JpaRepository<NurseHistory, Integer> {

    // 특정 간호사의 이력 조회 (최신순)
    List<NurseHistory> findByNurseMainOrderByCreateDateDesc(NurseMain nurseMain);

    // 전체 이력 조회 (페이징, 최신순)
    Page<NurseHistory> findAllByOrderByCreateDateDesc(Pageable pageable);

    // 특정 기간의 이력 조회
    @Query("SELECT nh FROM NurseHistory nh WHERE nh.createDate BETWEEN :startDate AND :endDate ORDER BY nh.createDate DESC")
    List<NurseHistory> findByCreateDateBetween(@Param("startDate") LocalDateTime startDate,
                                                                           @Param("endDate") LocalDateTime endDate);

    // 특정 작성자의 이력 조회
    List<NurseHistory> findByWriterOrderByCreateDateDesc(String writer);

    // 특정 수정자의 이력 조회
    List<NurseHistory> findByModifierOrderByCreateDateDesc(String modifier);

    // 간호사 이름으로 검색
    @Query("SELECT nh FROM NurseHistory nh WHERE nh.firstName LIKE %:name% OR nh.lastName LIKE %:name% ORDER BY nh.createDate DESC")
    List<NurseHistory> findByNurseNameContaining(@Param("name") String name);
}