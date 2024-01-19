package com.aitschool.user.Rabc.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rabc_permission_role")
public class PermissionRole {

    @Id
    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
