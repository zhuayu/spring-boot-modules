package com.aitschool.user.Rabc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rabc_role_administrators")
public class RoleAdministrator {

    @Id
    @ManyToOne
    @JoinColumn(name = "administrator_id")
    @JsonBackReference
    private Administrator administrator;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
