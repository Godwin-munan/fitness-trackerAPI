/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.constant;


/**
 *
 * @author godwi
 */
public class Authority {
    public static final String[] USER_AUTHORITIES = { "user:read" };
    public static final String[] HR_AUTHORITIES = { "user:read", "user:update" };
    public static final String[] MANAGER_AUTHORITIES = { "user:read", "user:update" };
    public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update" };
    public static final String[] SUPER_ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update", "user:delete" };
}
