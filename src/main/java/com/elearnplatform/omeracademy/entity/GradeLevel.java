package com.elearnplatform.omeracademy.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "grade_levels")
public class GradeLevel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String gradeLevelName;

    @OneToMany(mappedBy = "gradeLevel",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> courses;

    @OneToMany(mappedBy = "gradeLevel",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> students;
}
