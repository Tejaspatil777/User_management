package com.example.usermanagement.dtos.requestDto;

import lombok.Getter;

@Getter
public class RegisterRequestDto {

    // Getters
    private String name;
    private String email;
    private String password;
    private String roleName;
    private Long roleId;

    public RegisterRequestDto() {}

    public void setName(String name) {
        this.name = name; }

    public void setEmail(String email) {
        this.email = email; }

    public void setPassword(String password) {
        this.password = password; }

    public void setRoleName(String roleName) {
        this.roleName = roleName; }

    public void setRoleId(Long roleId) {
        this.roleId = roleId; }
}
