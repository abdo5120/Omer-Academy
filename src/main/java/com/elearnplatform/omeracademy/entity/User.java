package com.elearnplatform.omeracademy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users")
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    //@Column(name = "email" , unique = true)
    //private String email;

    @Column(name = "user_name", unique = true, nullable = false)
    private String username;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING) //to tell hibernate(orm) that store value of enum as string
    @Column(name = "role", nullable = false)
    private Role role = Role.STUDENT;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "grade_level_id")
    @JsonIgnore
    private GradeLevel gradeLevel;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Submission> submission;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+role.name()));
    }


    @Override
    public String getUsername()
    {
        return this.username;
    }

    @Override
    public String getPassword()
    {
        return this.passwordHash;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled()
    {
        return UserDetails.super.isEnabled();
    }
}
