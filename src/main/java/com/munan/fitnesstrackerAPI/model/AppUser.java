/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author godwi
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUser implements Serializable{
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @Column(name = "user_id")
    private String userId;

    @Column(name = "username")
    private String username;
    
    @Column(name = "birth_date")
    private Date birthday;

    
    @Column(name = "password")
    @JsonIgnore
    private String password;
    
    @Column(name = "join_date")
    @JsonIgnore
    private Date joinDate;
    
    @Column(name = "last_login_date")
    @JsonIgnore
    private Date lastLoginDate;

    @Column(name = "last_login_date_display")
    private Date lastLoginDateDisplay;

    @Column(name = "is_active")
    @JsonIgnore
    private boolean isActive;

    @Column(name = "is_not_locked")
    @JsonIgnore
    private boolean isNotLocked;
    
    @ManyToOne
    @JsonIgnore
    private Role role;

}
