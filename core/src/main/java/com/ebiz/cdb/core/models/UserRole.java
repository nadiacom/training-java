package com.ebiz.cdb.core.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "role")
@Table(name = "user_role")
public class UserRole implements Serializable, GrantedAuthority {

    @Id
    @GeneratedValue
    private Long id;
    private String role;
    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
