package com.elearnplatform.omeracademy.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    //@Column(name = "email" , unique = true)
    //private String email;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING) //to tell hibernate(orm) that store value of enum as string
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "grade_level_id")
    private GradeLevel gradeLevel;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Submission> submission;
}
