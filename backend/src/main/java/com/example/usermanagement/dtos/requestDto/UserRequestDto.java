package com.example.usermanagement.dtos.requestDto;

import lombok.Setter;

public class UserRequestDto {

    @Setter
    private String name;
    @Setter
    private String email;
    @Setter
    private String password;
    @Setter
    private String roleName;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRoleName() {
        return roleName;
    }

}
