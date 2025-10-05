package com.elearnplatform.omeracademy.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

public enum Role implements GrantedAuthority
{
    ADMIN,
    STUDENT;

    @Override
    public String getAuthority()
    {
        return "";
    }
}
