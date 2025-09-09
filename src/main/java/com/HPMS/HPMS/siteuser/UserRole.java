package com.HPMS.HPMS.siteuser;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    SYSTEM("ROLE_SYSTEM"),
    DOCTOR("ROLE_DOCTOR"),
    NURSE("ROLE_NURSE"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
