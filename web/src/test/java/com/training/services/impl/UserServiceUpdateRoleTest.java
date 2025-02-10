package com.training.services.impl;

import com.training.config.AppConstants;
import com.training.models.AppRole;
import com.training.models.Role;
import com.training.models.User;
import com.training.repositories.RoleRepository;
import com.training.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceUpdateRoleTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUserName("testuser");

        role = new Role();
        role.setRoleId(1);
        role.setRoleName(AppRole.ROLE_ADMIN);
    }

//    @Test
//    void updateUserRole_ShouldUpdateRole_WhenUserAndRoleExist() {
//        // Arrange
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(roleRepository.findByRoleName(AppRole.ROLE_ADMIN)).thenReturn(Optional.of(role));
//
//        // Act
//        userService.updateUserRole(1L, "ADMIN");
//
//        // Assert
//        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
//        verify(userRepository).save(userCaptor.capture());
//        User updatedUser = userCaptor.getValue();
//
//        assertEquals(role, updatedUser.getRole());
//        verify(userRepository).findById(1L);
//        verify(roleRepository).findByRoleName(AppRole.ROLE_ADMIN);
//        verify(userRepository).save(user);
//    }

    @Test
    void updateUserRole_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUserRole(1L, "ADMIN");
        });

        assertEquals(AppConstants.USER_NOT_FOUND, exception.getMessage());
        verify(userRepository).findById(1L);
        verify(roleRepository, never()).findByRoleName(any(AppRole.class));
        verify(userRepository, never()).save(any(User.class));
    }

//    @Test
//    void updateUserRole_ShouldThrowException_WhenRoleNotFound() {
//        // Arrange
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(roleRepository.findByRoleName(AppRole.ROLE_ADMIN)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            userService.updateUserRole(1L, "ADMIN");
//        });
//
//        assertEquals(AppConstants.ROLE_NOT_FOUND, exception.getMessage());
//        verify(userRepository).findById(1L);
//        verify(roleRepository).findByRoleName(AppRole.ROLE_ADMIN);
//        verify(userRepository, never()).save(any(User.class));
//    }
}