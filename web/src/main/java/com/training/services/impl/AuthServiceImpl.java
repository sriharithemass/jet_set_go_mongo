package com.training.services.impl;

import com.training.config.AppConstants;
import com.training.exception.APIException;
import com.training.models.AppRole;
import com.training.models.Role;
import com.training.models.User;
import com.training.repositories.RoleRepository;
import com.training.repositories.UserRepository;
import com.training.security.jwt.JwtUtils;
import com.training.security.request.LoginRequest;
import com.training.security.request.SignupRequest;
import com.training.security.response.LoginResponse;
import com.training.security.response.MessageResponse;
import com.training.security.response.UserInfoResponse;
import com.training.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    UserService userService;

    public AuthServiceImpl(JwtUtils jwtUtils, AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.userService = userService;
    }

    public LoginResponse authenticateUser(LoginRequest loginRequest){
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            throw new APIException(AppConstants.INVALID_CREDENTIALS);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(userDetails.getUsername(),
                roles, jwtToken);

        return response;
    }

    public MessageResponse registerUser(SignupRequest signUpRequest){
        if (userRepository.existsByUserName(signUpRequest.getUsername())) {
            throw new APIException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new APIException("Error: Email is already in use!");
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Role role;

        if (strRoles == null || strRoles.isEmpty()) {
            role = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(AppConstants.ROLE_NOT_FOUND));
        } else {
            String roleStr = strRoles.iterator().next();
            if (roleStr.equals("admin")) {
                role = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException(AppConstants.ROLE_NOT_FOUND));
            } else {
                role = roleRepository.findByRoleName(AppRole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException(AppConstants.ROLE_NOT_FOUND));
            }
        }
        user.setRole(role);
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }

    public UserInfoResponse getUserDetails(String username){
        User user = userService.findByUsername(username);

        UserInfoResponse response = new UserInfoResponse(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getRole().getRoleName().toString()
        );

        return response;
    }

    public String currentUserName(String username){
        if (!userRepository.existsByUserName(username))
            throw new APIException(AppConstants.USER_NOT_FOUND);

        return username;
    }
}
