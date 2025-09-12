// DocRankRepository.java
package com.HPMS.HPMS.global.Job_Code;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface Doc_Rank_Repository extends JpaRepository<Doc_Rank, Integer> {
    List<Doc_Rank> findAllByOrderByKorNameAsc();
}
