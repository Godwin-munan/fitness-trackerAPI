/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.munan.fitnesstrackerAPI.enums;

import static com.munan.fitnesstrackerAPI.constant.Authority.*;


/**
 *
 * @author godwi
 */
public enum Roles {
    
    ROLE_USER(USER_AUTHORITIES),
    ROLE_HR(HR_AUTHORITIES),
    ROLE_MANAGER(MANAGER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES),
    ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);
    
    
    private final String[] authorities;
    
    Roles(String... authorities){
        this.authorities = authorities;
    }
    
    public String[] getAuthorities() {
        return authorities;
    }
}
