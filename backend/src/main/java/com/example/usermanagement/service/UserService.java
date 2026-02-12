package com.example.usermanagement.service;

import com.example.usermanagement.dtos.responseDto.UserResponseDTO;
import com.example.usermanagement.exception.BusinessException;
import com.example.usermanagement.exception.ResourceNotFoundException;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.RoleRepository;
import com.example.usermanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // CREATE
    public User create(User user, String roleName) {


        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("Email already exists");
        }

        if (user.getPassword().length() < 5) {
            throw new BusinessException("Password must be at least 5 characters");
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);

        return userRepository.save(user);
    }

    // READ ALL

    public List<UserResponseDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole().getName()
                ))
                .toList();
    }

    // READ BY ID
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // UPDATE
    public User update(Long id, User updated, String roleName) {

        User existing = getById(id);

        if (!existing.getEmail().equals(updated.getEmail())
                && userRepository.existsByEmail(updated.getEmail())) {
            throw new BusinessException("Email already exists");
        }

        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());

        if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updated.getPassword()));
        }

        if (roleName != null) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            existing.setRole(role);
        }

        return userRepository.save(existing);
    }

    // DELETE
    public void delete(Long id) {
        User user = getById(id);
        userRepository.delete(user);
    }
}
