/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.service;

import static com.munan.fitnesstrackerAPI.constant.SecurityConstant.ACCESS_TOKEN;
import static com.munan.fitnesstrackerAPI.constant.UserConstant.INVALID_USERNAME;
import static com.munan.fitnesstrackerAPI.constant.UserConstant.USERNAME_ALREADY_EXIST;
import static com.munan.fitnesstrackerAPI.constant.UserConstant.USER_NOT_FOUND_BY_USERNAME;
import com.munan.fitnesstrackerAPI.dto.LoginRequest;
import com.munan.fitnesstrackerAPI.enums.Roles;
import com.munan.fitnesstrackerAPI.exceptions.AlreadyExistException;
import com.munan.fitnesstrackerAPI.exceptions.NotFoundException;
import com.munan.fitnesstrackerAPI.model.AppUser;
import com.munan.fitnesstrackerAPI.model.Role;
import com.munan.fitnesstrackerAPI.repository.AppUserRepository;
import com.munan.fitnesstrackerAPI.utils.ApiResponse;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.HOURS;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import static org.springframework.security.oauth2.jose.jws.MacAlgorithm.*;

/**
 *
 * @author godwi
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final LoginAttemptService loginAttemptService;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder encoder;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AppUserService.class);
    
    
    //AUTHENTICATE AND LOGIN USER
    public ResponseEntity<ApiResponse<?>> login(LoginRequest login) throws NotFoundException {

        Authentication authentication = authenticate(login.getUsername(), login.getPassword());
        
        AppUser user = findUserByUsername(login.getUsername());
        
        Instant now = Instant.now();
        List<String> scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("FitnessTracker")
                .issuedAt(now)
                .expiresAt(now.plus(10, HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
 /*  
        **Asyme
        **

        JwtEncoderParameters encoderParameters = JwtEncoderParameters.from(claims);
 */
 /*
 **Sym
*/
        var encoderParameters = JwtEncoderParameters.from(JwsHeader.with(HS256).build(), claims);

        
 
        String token = this.encoder.encode(encoderParameters).getTokenValue();
        
        HttpHeaders jwtHeaders = getJwtHeader(token);

        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(), 
                        token,
                        user),
                jwtHeaders,
                OK
        );
    }
    
    //    REGISTER NEW USER
    public ResponseEntity register(LoginRequest register) throws NotFoundException, AlreadyExistException {

        validateNewUsername(register.getUsername());
        Role role = validateRole(Roles.ROLE_SUPER_ADMIN.name());
        AppUser user = new AppUser();
        user.setUserId("FT" + generateUserId());
        user.setUsername(register.getUsername());
        user.setPassword(encodePassword(register.getPassword()));
        user.setBirthday(register.getBirthday());
        user.setJoinDate(LocalDate.now());
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(role);
        AppUser savedUser = userRepository.save(user);
        
        
        //Start OF LOGIN
        Authentication authentication = authenticate(register.getUsername(), register.getPassword());
  
        
        Instant now = Instant.now();
        List<String> scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("FitnessTracker")
                .issuedAt(now)
                .expiresAt(now.plus(10, HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

 /*  
        **Asyme
        **

    JwtEncoderParameters encoderParameters = JwtEncoderParameters.from(claims);
 */

/*
 **Sym
 */
        var encoderParameters = JwtEncoderParameters.from(JwsHeader.with(HS256).build(), claims);

 
        String token = this.encoder.encode(encoderParameters).getTokenValue();
        
        HttpHeaders jwtHeaders = getJwtHeader(token);
        //END OF LOGIN
        
        return new ResponseEntity<>(
                new ApiResponse<>(
                        OK.value(), 
                        token, 
                        savedUser
                ),
                jwtHeaders,
                OK
        );

    }
    
    
    private HttpHeaders getJwtHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ACCESS_TOKEN, token);
        return headers;
    }
    
    public AppUser findUserByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()-> new NotFoundException(USER_NOT_FOUND_BY_USERNAME+username));
    }
    
    
    private Roles getRoleEnumName(String role) {
        return Roles.valueOf(role.toUpperCase());
    }


    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(6);
    }

    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }
    
    public AppUser findUserById(Long id) throws NotFoundException{
        Optional<AppUser> existing = userRepository.findById(id);
        if (existing.isEmpty()) {
            throw new NotFoundException("No user found by userid: " + id);
        }
        return existing.get();        
    }

    public AppUser findUserByUserID(String userID) throws NotFoundException {
        Optional<AppUser> existing = userRepository.findByUserId(userID);
        if (existing.isEmpty()) {
            throw new NotFoundException("No user found by userid: " + userID);
        }
        return existing.get();
    }


    private Authentication authenticate(String username, String password) {

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
    
    
    private AppUser validateNewUsername(String newUsername) throws NotFoundException, AlreadyExistException {    
        if (StringUtils.isNotBlank(newUsername)) {
            Optional<AppUser> userByNewUsername = userRepository.findByUsername(newUsername);
            
            if(userByNewUsername.isPresent()){
                throw new AlreadyExistException(USERNAME_ALREADY_EXIST);
            }else {
                return null;
            }
            
        } else {
            
            throw new NotFoundException(INVALID_USERNAME);
        }
    }
    
    private Role validateRole(String role) throws NotFoundException{
        return this.roleService.findRoleByName(role);
    }
    
}
