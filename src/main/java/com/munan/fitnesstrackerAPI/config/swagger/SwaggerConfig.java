/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author godwi
 */
@OpenAPIDefinition(info = @Info(title = "Fitness Tracker API", version = "1.0", description = "Fitness Tracker API"))
@SecurityScheme(name = "Authorization", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@Configuration
public class SwaggerConfig {
    
}
