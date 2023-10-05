package com.drawproject.dev.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Courses extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int courseId;

    @NotBlank(message = "Title can not be blank")
    @Size(min = 6, message = "Title must be at least 6 characters long")
    private String courseTitle;

    private String description;

    private String information;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,targetEntity = Skill.class)
    @JoinColumn(name = "skill_id", referencedColumnName = "skillId", nullable = false)
    private Skill skill;

    private int price;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, targetEntity = Category.class)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId", nullable = false)
    private Category category;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, targetEntity = Style.class)
    @JoinColumn(name = "rolling_style_id", referencedColumnName = "rollingStyleId", nullable = false)
    private Style style;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @NotBlank
    private byte[] image;

    private String status;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinColumn(name = "instructor_id", referencedColumnName = "userId", nullable = false)
    private User instructor;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy ="courses", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Feedback> feedback;

    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Topic> topics;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Orders> orders = new HashSet< Orders>();

    @Transient
    private double averageStar;

    @Transient
    private int numLesson;

    @OneToMany(mappedBy = "course")
    private List<OrderDetail> orderDetails;

    public double getAverageStar() {
        if(this.feedback != null && !this.feedback.isEmpty()) {
            double totalStars = 0;
            for (Feedback f : this.feedback) {
                totalStars += f.getStar();
            }
            this.averageStar = totalStars / this.feedback.size();
        } else {
            this.averageStar = 0;
        }
        return averageStar;
    }

    public int getNumLesson() {
        if(this.topics != null) {
            int count = this.topics.stream().mapToInt(topic -> (topic != null) ? (topic.getLessons().size()) : 0).sum();
            this.numLesson = count;
        } else {
            this.numLesson = 0;
        }
        return this.numLesson;
    }

    public Courses(String courseTitle, String description, String information, int price, byte[] image, String status) {
        this.courseTitle = courseTitle;
        this.description = description;
        this.information = information;
        this.price = price;
        this.image = image;
        this.status = status;
    }
}
