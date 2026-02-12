package com.example.usermanagement.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<User> users;

    // ===== Getters =====
    public Long getId() {
        return id; }
    public String getName() {
        return name; }
    public List<User> getUsers() {
        return users; }

    // ===== Setters =====
    public void setId(Long id) {
        this.id = id; }
    public void setName(String name) {
        this.name = name; }
    public void setUsers(List<User> users) {
        this.users = users; }
}
