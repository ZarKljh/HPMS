package com.HPMS.HPMS.Doctor.DoctorM;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
    public interface DoctorMRepository extends JpaRepository<DoctorM, Integer> {
    /*
    DoctorM findBySubject(String subject);
    DoctorM findBySubjectAndContent(String subject, String content);
    List<DoctorM> findBySubjectLike(String subject);
    Page<DoctorM> findAll(Pageable pageable);
    @Query("select "
            + "distinct q "
            + "from Question q "
            + "left outer join SiteUser u1 on q.author=u1 "
            + "left outer join Answer a on a.question=q "
            + "left outer join SiteUser u2 on a.author=u2 "
            + "where "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.username like %:kw% ")

    Page<DoctorM> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
*/
}

