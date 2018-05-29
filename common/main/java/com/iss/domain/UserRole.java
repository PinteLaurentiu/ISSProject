package com.iss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iss.enums.Role;

public class UserRole {
    private int idRole;
    @JsonIgnore
    private User user;
    private Role role;

    public UserRole() {
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
