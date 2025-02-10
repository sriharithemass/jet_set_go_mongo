package com.training.services;

import com.training.payload.UserDTO;
import com.training.models.Role;
import com.training.models.User;

import java.util.List;

public interface UserService {
    void updateUserRole(String userId, String roleName);
    List<User> getAllUsers();
    UserDTO getUserById(String id);
    User findByUsername(String username);
    List<Role> getAllRoles();
    void updatePassword(String userId, String password);
}
