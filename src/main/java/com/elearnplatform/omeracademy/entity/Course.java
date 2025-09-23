package com.elearnplatform.omeracademy.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "courses")
public class Course
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob // For longer text
    @Column(name = "description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "grade_level_id", nullable = false)
    private GradeLevel gradeLevel;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lesson> lessons;
}
