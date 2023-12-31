package com.drawproject.dev.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Feedback extends BaseEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int feedbackId;

    @NotBlank(message="Infomation must not be blank")
    @Size(min=10, message="Name must be at least 3 characters long")
    private String feedbackInformation;

    private String status;

    private int star;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST, targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "userId",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "course_id", referencedColumnName = "courseId", nullable = true)
    private Courses courses;

}
