package com.example.usermanagement.service;

import com.example.usermanagement.model.Role;
import com.example.usermanagement.repository.RoleRepository;
import com.example.usermanagement.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getByName(String name) {
        return roleRepository.findByNameIgnoreCase(name.trim())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found: " + name));
    }
}
