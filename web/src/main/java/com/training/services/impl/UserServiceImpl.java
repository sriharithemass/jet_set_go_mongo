package com.training.services.impl;

import com.training.config.AppConstants;
import com.training.exception.APIException;
import com.training.payload.UserDTO;
import com.training.models.AppRole;
import com.training.models.Role;
import com.training.models.User;
import com.training.repositories.RoleRepository;
import com.training.repositories.UserRepository;
import com.training.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void updateUserRole(String userId, String roleName) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new APIException(AppConstants.USER_NOT_FOUND);
        }

        AppRole appRole = AppRole.valueOf(roleName);
        Role role = roleRepository.findByRoleName(appRole)
                .orElseThrow(() -> new RuntimeException(AppConstants.ROLE_NOT_FOUND));
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public UserDTO getUserById(String id) {
        User user = userRepository.findByUserId(id);

        if (user == null) {
            throw new APIException(AppConstants.USER_NOT_FOUND);
        }

        return convertToDto(user);
    }

    private UserDTO convertToDto(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedDate(),
                user.getUpdatedDate()
        );
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUserName(username);
        return user.orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void updatePassword(String userId, String password) {
        try {
            User user = userRepository.findByUserId(userId);

            if (user == null) {
                throw new APIException(AppConstants.USER_NOT_FOUND);
            }

            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        } catch (Exception e) {
            throw new APIException("Failed to update password");
        }
    }
}
