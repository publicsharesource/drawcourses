package com.drawproject.dev.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "user_assignment")
public class UserAssignment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private int taskId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enroll_id", referencedColumnName = "enrollId", nullable = false)
    private Enroll enroll;

    @Size(max = 255)
    @NotNull
    @Column(name = "task_title", nullable = false)
    private String taskTitle;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "image", nullable = false)
    private String image;

    private String status;

    private Integer grade;

    private String comment;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignment_id", referencedColumnName = "assignmentId", nullable = false)
    private Assignment assignment;

}