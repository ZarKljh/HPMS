package com.HPMS.HPMS.siteuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteUserRepository extends JpaRepository<SiteUser, Integer> {
    Optional<SiteUser> findByUserId(String userId);
}
