package com.HPMS.HPMS.global.Country_Code;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface Country_Code_Repository extends JpaRepository<Country_Code, Integer> {

    @Query("""
        select c from Country_Code c
        where
          ( :kw is null
            or lower(c.countryKr)      like lower(concat('%', :kw, '%'))
            or lower(c.countryEn)      like lower(concat('%', :kw, '%'))
            or lower(c.isoAlpha2)      like lower(concat('%', :kw, '%'))
            or lower(c.isoAlpha3)      like lower(concat('%', :kw, '%'))
            or lower(c.continentAdmin) like lower(concat('%', :kw, '%'))
            or lower(c.continentMofa)  like lower(concat('%', :kw, '%'))
            or lower(c.continentCommon) like lower(concat('%', :kw, '%'))
          )
          and ( :num is null or c.numeric = :num )
        """)
    Page<Country_Code> search(@Param("kw") String kw,
                              @Param("num") Integer num,
                              Pageable pageable);
}