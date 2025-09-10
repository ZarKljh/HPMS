// src/main/java/com/HPMS/HPMS/global/Address_Code/Address_Code_Repository.java
package com.HPMS.HPMS.global.Address_Code;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Address_Code_Repository extends JpaRepository<Address_Code, Integer> {

    @Query("""
      select a from Address_Code a
      where (:kw is null or :kw = ''
            or lower(a.road_name_kor) like lower(concat('%', :kw, '%'))
            or lower(a.road_name_eng) like lower(concat('%', :kw, '%'))
            or lower(a.sido_kor)      like lower(concat('%', :kw, '%'))
            or lower(a.sgg_kor)       like lower(concat('%', :kw, '%'))
            or lower(a.emd_kor)       like lower(concat('%', :kw, '%'))
            or lower(a.road_code)     like lower(concat('%', :kw, '%'))
            or lower(a.emd_seq_no)    like lower(concat('%', :kw, '%'))
      )
      """)
    Page<Address_Code> search(@Param("kw") String kw, Pageable pageable);
}
