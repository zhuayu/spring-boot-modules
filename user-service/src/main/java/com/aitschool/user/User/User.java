package com.aitschool.user.User;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(36)")
    private String uuid;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String unionid;

    @Column(unique = true)
    private String phone;

    private String password;
    private String nickname;
    private String avatarUrl;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "visited_at")
    private Date visitedAt;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }
}