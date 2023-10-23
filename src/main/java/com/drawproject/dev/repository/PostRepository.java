package com.drawproject.dev.repository;

import com.drawproject.dev.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer> {

    List<Posts> findByStatus(String status);

    Posts findPostsByPostId(int postId);

    Page<Posts> findByStatus(String status, Pageable pageable);

    Page<Posts> findAll(Pageable pageable);

    List<Posts> findPostsByUserUserId(int userId);
    Page<Posts> findByUserUserIdAndStatus(int userId, String status, Pageable pageable);
}
