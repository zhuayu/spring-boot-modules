package com.aitschool.user.Rabc.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "rabc_permission_groups")
public class PermissionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    private Long sort;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Permission> permissions;
}
