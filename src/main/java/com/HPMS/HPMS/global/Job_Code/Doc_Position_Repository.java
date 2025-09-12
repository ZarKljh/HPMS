// DocPositionRepository.java
package com.HPMS.HPMS.global.Job_Code;

import com.HPMS.HPMS.global.Job_Code.Doc_Position;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface Doc_Position_Repository extends JpaRepository<Doc_Position, Integer> {
    List<Doc_Rank> findAllByOrderByKorNameAsc();
}
