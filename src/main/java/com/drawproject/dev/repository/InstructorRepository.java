package com.drawproject.dev.repository;

import com.drawproject.dev.dto.instructor.BestInstructor;
import com.drawproject.dev.model.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    Optional<Instructor> findInstructorByInstructorId(int userId);

    @Query(value = "SELECT t1.instructor_id, t3.full_name, t3.avatar, t1.numOfStudent, t2.numOfStar " +
            "FROM (" +
            "   SELECT courses.instructor_id, COUNT(enroll.enroll_id) AS numOfStudent " +
            "   FROM courses JOIN enroll " +
            "   ON courses.course_id = enroll.course_id " +
            "   GROUP BY courses.instructor_id) AS t1\n " +
            "JOIN (" +
            "    SELECT courses.instructor_id, COALESCE(AVG(feedback.star), 0) AS numOfStar " +
            "    FROM courses " +
            "    LEFT JOIN feedback ON courses.course_id = feedback.course_id " +
            "    GROUP BY courses.instructor_id " +
            ") AS t2 ON t1.instructor_id = t2.instructor_id " +
            "JOIN users AS t3 " +
            "ON t3.user_id = t1.instructor_id " +
            "ORDER BY t1.numOfStudent, t2.numOfStar DESC LIMIT 3", nativeQuery = true)
    List<Object[]> findTopInstructors();

}
