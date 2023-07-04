package com.example.demo3.service.interfaces;

import com.example.demo3.model.Role;
import com.example.demo3.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserById(Long id);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    List<Role> getAllRoles();
}
