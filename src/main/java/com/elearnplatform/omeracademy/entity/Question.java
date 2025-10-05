package com.elearnplatform.omeracademy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "questions")
public class Question
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Lob
    @Column(name = "question_text")
    private String questionText;

    @Column(name = "options", columnDefinition = "json")
    private String options;

    @Column(name = "correct_ answer")
    private String correctAnswer;

    @ManyToOne
    @JoinColumn(name = "quiz_id",nullable = false)
    @JsonIgnore
    private Quiz quiz;
}
