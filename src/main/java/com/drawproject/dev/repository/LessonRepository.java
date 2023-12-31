package com.drawproject.dev.repository;

import com.drawproject.dev.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByTopicTopicIdAndStatus(int topicId, String status);

    int countByTopicCourseCourseIdAndStatus(int courseId, String status);

    Lesson findByNumberAndTopicNumberAndTopicCourseCourseIdAndStatus(int indexLesson, int indexTopic, int courseId, String status);

    List<Lesson> findByTopicTopicIdAndStatusAndLessonIdNotIn(int topicId, String status, List<Integer> lessonIds);

    List<Lesson> findByProcessesEnrollEnrollIdAndTopicCourseCourseIdAndStatus(int enrollId, int courseId, String status);
    List<Lesson> findByTopicTopicIdAndStatusOrderByNumber(int topicId, String status);
}
