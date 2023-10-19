INSERT INTO `skills` (`skill_name`) VALUES
('Mới bắt đầu'),
('Trung cấp'),
('Nâng cao'),
('Chuyên nghiệp');

INSERT INTO `category` (`category_name`) VALUES
("Pop Art"),
("Tối giản"),
("Trừu tượng"),
("Hiện thực"),
("Siêu thực");


INSERT INTO `drawing_style` (`drawing_style_name`) VALUES
("Thiết kế 3D"),
("Thiết kế trừu tượng"),
("Thiết kế tối giản"),
("Conceptual art - Thiết kế nghệ thuật khái niệm"),
("Thiết kế vui nhộn"),
("Thiêt kế minh họa"),
("Thiết kế Vintage"),
("Thiết kế tự nhsiên");

   INSERT INTO `roles` (`role_name`)
  VALUES
    ("Admin"),
    ("Staff"),
    ("Instructor"),
    ("Customer");

INSERT INTO `users` (`user_id`, `username`, `avatar`, `pwd`, `email`, `mobile_num`, `status`, `skill_id`, `role_id`, `created_at`, `updated_at`, `full_name`)
VALUES
    (1, 'newuser1', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user1@example.com', '1234567890', 'OPEN', 1, 2, '2023-09-19 09:00:00', '2023-09-19 09:00:00', 'newuser1'),
    (2, 'newuser2', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user2@example.com', NULL, 'OPEN', 2, 2, '2023-09-20 10:15:00', '2023-09-20 10:15:00', 'newuser2'),
    (3, 'newuser3', 'avatar_blob_data', '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user3@example.com', '9876543210', 'OPEN', 3, 3, '2023-09-21 14:30:00', '2023-09-21 14:30:00', 'newuser3'),
    (4, 'admin1', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'admin@example.com', '555-123-4567', 'OPEN', 4, 1, '2023-09-22 16:45:00', '2023-09-22 16:45:00', 'admin1'),
    (5, 'newuser4', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user4@example.com', '111-222-3333', 'OPEN', 4, 4, '2023-09-23 18:00:00', '2023-09-23 18:00:00', 'newuser4'),
    (6, 'newuser6', 'avdawlob_data', '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user3@example.com', '9876543210', 'OPEN', 3, 3, '2023-09-21 14:30:00', '2023-09-21 14:30:00', 'newuser6'),
    (7, 'newuser7', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user4@example.com', '111-222-3333', 'OPEN', 4, 3, '2023-09-23 18:00:00', '2023-09-23 18:00:00', 'newuser7'),
    (8, 'newuser8', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user4@example.com', '111-222-3333', 'OPEN', 4, 4, '2023-09-23 18:00:00', '2023-09-23 18:00:00', 'newuser8'),
    (9, 'newuser9', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user4@example.com', '111-222-3333', 'OPEN', 4, 4, '2023-09-23 18:00:00', '2023-09-23 18:00:00', 'newuser9'),
    (10, 'newuser10', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user4@example.com', '111-222-3333', 'OPEN', 4, 4, '2023-09-23 18:00:00', '2023-09-23 18:00:00', 'newuser101');

INSERT INTO instructors (instructor_id, bio, payment, education)
VALUES
    (3, 'Experienced math tutor specializing in calculus and algebra.', 'PayPal', 'Ph.D. in Mathematics'),
    (6, 'Certified yoga instructor with 10 years of teaching experience.', 'Venmo', 'Yoga Alliance Certification'),
    (7, 'Professional guitar teacher offering lessons in various styles.', 'Cash', 'Bachelor of Music in Guitar Performance');

-- Thêm dữ liệu vào bảng 'courses' liên quan đến học vẽ
INSERT INTO `courses` (`course_id`, `course_title`, `description`, `information`, `skill_id`, `price`, `category_id`, `status`, `image`, `drawing_style_id`, `instructor_id`)
VALUES
    (1, 'Vẽ Biển Và Cát', 'Học cách vẽ phong cảnh biển và cát bằng màu nước.', 'Khóa học này dành cho người mới học vẽ.', 1, 49, 1, 'Open', 'beach.jpg', 1, 3),
    (2, 'Vẽ Hoa Tulip', 'Tìm hiểu cách vẽ hoa tulip bằng pastel màu nước.', 'Khóa học này phù hợp cho tất cả mọi người.', 2, 29, 1, 'Open', 'tulip.jpg', 2, 6),
    (3, 'Tranh Sơn Dầu Cảnh Đêm', 'Học kỹ thuật vẽ tranh sơn dầu cảnh đêm.', 'Cần kiến thức vẽ sơn dầu cơ bản.', 3, 79, 2, 'Open', 'night.jpg', 3, 7),
    (4, 'Vẽ Chân Dung', 'Học cách vẽ chân dung nghệ thuật với bút chì và màu nước.', 'Yêu cầu kiến thức về vẽ căn bản.', 4, 59, 2, 'Open', 'portrait.jpg', 4, 3),
    (5, 'Vẽ Graffiti Đường Phố', 'Học cách tạo nghệ thuật graffiti độc đáo.', 'Không cần kinh nghiệm trước đây.', 1, 39, 3, 'Open', 'graffiti.jpg', 5, 7);

-- Thêm dữ liệu vào bảng 'topic' và 'lesson'
-- Thêm dữ liệu vào bảng 'topic' và 'lesson' liên quan đến học vẽ
INSERT INTO `topic` (`topic_id`, `topic_title`, `course_id`)
VALUES
    (1, 'Vẽ bãi biển', 1),
    (2, 'Vẽ cảnh người đánh cá', 1),
    (3, 'Vẽ sóng', 1),
    (4, 'Vẽ hoa tulip', 2),
    (5, 'Vẽ cảnh phụ', 2),
    (6, 'Vẽ tranh sơn dầu cảnh đêm', 3),
    (7, 'Chân dung người mẫu', 4),
    (8, 'Vẽ graffiti phố đêm', 5);

INSERT INTO `lesson` (`lesson_id`, `url`, `topic_id`, `name`, `type_file`, `created_at`, `updated_at`)
VALUES
    (1, 'lesson1.mp4', 1, 'Vẽ biển và cát', 'video', '2023-09-19 10:00:00', '2023-09-19 10:30:00'),
    (2, 'lesson2.mp4', 1, 'Vẽ trời và chi tiết phụ', 'video', '2023-09-19 10:00:00', '2023-09-19 10:30:00'),
    (3, 'lesson1.mp4', 2, 'Sử dụng pastel màu nước', 'video', '2023-09-19 10:45:00', '2023-09-19 11:15:00'),
    (4, 'lesson2.mp4', 2, 'Vẽ người và thuyền', 'video', '2023-09-19 10:45:00', '2023-09-19 11:15:00'),
    (5, 'lesson1.pdf', 3, 'Sử dụng kết hợp các cách đã học', 'pdf', '2023-09-20 09:30:00', '2023-09-20 10:00:00'),
    (6, 'lesson1.mp4', 4, 'Chân dung và biểu cảm', 'video', '2023-09-21 14:00:00', '2023-09-21 14:30:00'),
    (7, 'lesson2.docx', 5, 'Các công cụ và kỹ thuật graffiti', 'document', '2023-09-22 11:00:00', '2023-09-22 11:45:00'),
    (8, 'lesson1.mp4', 6, 'Vẽ cảnh trời', 'video', '2023-09-23 15:00:00', '2023-09-23 15:45:00'),
    (9, 'lesson2.pdf', 6, 'Vẽ cây cỏ bằng bút chì', 'pdf', '2023-09-24 10:30:00', '2023-09-24 11:15:00'),
    (10, 'lesson1.pdf', 7, 'Kĩ thuật phác họa hình ảnh', 'pdf', '2023-09-24 10:30:00', '2023-09-24 11:15:00'),
    (11, 'lesson2.mp4', 7, 'Vẽ chi tiết trên khuôn mặt', 'video', '2023-09-24 10:30:00', '2023-09-24 11:15:00'),
    (12, 'lesson1.pdf', 8, 'Vẽ phong cảnh và phác hoạt dòng người', 'pdf', '2023-09-24 10:30:00', '2023-09-24 11:15:00');

# Tạo 10 bản ghi
INSERT INTO feedback (feedback_information, status, star, created_at, course_id, user_id)
VALUES
  ('Giảng viên giảng bài rất hay, dễ hiểu và nhiệt tình.', 'OPEN', 5, '2023-09-20 23:12:49', 1, 1),
  ('Nội dung khóa học rất hữu ích, giúp tôi học được nhiều kiến thức mới.', 'OPEN', 5, '2023-09-21 00:00:00', 2, 3),
  ('Tôi có một góp ý nhỏ về khóa học. Tôi nghĩ sẽ tốt hơn nếu giảng viên có thể cung cấp thêm một số bài tập thực hành.', 'OPEN', 4, '2023-09-21 01:00:00', 3, 4),
  ('Tôi rất hài lòng với khóa học này. Tôi đã học được nhiều điều và tôi sẽ giới thiệu khóa học này cho bạn bè của tôi.', 'OPEN', 5, '2023-09-21 02:00:00', 4, 7),
  ('Tôi cảm thấy khóa học này hơi khó, nhưng tôi cũng học được nhiều thứ. Tôi hy vọng giảng viên có thể cung cấp thêm một số tài liệu tham khảo.', 'OPEN', 4, '2023-09-21 03:00:00', 5, 1),
  ('Tôi không hài lòng với khóa học này. Tôi nghĩ nội dung khóa học không được cập nhật và giảng viên không nhiệt tình.', 'OPEN', 2, '2023-09-21 04:00:00', 5, 2),
  ('Tôi hy vọng giảng viên có thể cung cấp thêm một số bài tập nhóm để chúng tôi có thể thực hành cùng nhau.', 'OPEN', 4, '2023-09-21 05:00:00', 1, 3),
  ('Tôi nghĩ khóa học này rất hay và hữu ích. Tôi đã học được nhiều kiến thức mới và tôi có thể áp dụng nó vào công việc của mình.', 'OPEN', 5, '2023-09-21 06:00:00', 1, 4),
  ('Tôi có một thắc mắc về bài tập thực hành. Tôi có thể liên hệ với giảng viên để được giải đáp không?', 'OPEN', 4, '2023-09-21 07:00:00', 4, 2);


INSERT INTO `enroll` (`enroll_id`, `user_id`, `course_id`, `status`)
VALUES
    (1, 5, 1, 'Enroll'),
    (2, 8, 2, 'Enroll'),
    (3, 9, 3, 'Enroll'),
    (4, 10, 4, 'Enroll'),
    (5, 5, 5, 'Enroll'),
    (6, 8, 1, 'Enroll'),
    (7, 9, 2, 'Enroll'),
    (8, 10, 3, 'Enroll'),
    (9, 5, 4, 'Enroll'),
    (10, 8, 5, 'Enroll');


INSERT INTO experience (drawing_style_id, instructor_id)
VALUES
    (1, 3),
    (2, 6),
    (3, 7),
    (4, 3),
    (5, 6),
    (6, 7),
    (7, 3),
    (8, 6),
    (1, 7),
    (2, 3);

INSERT INTO certificates (certificate_id, image, instructor_id, status)
VALUES
    (1, "image", 3, "ACCEPT"),
    (2, "image", 6, "ACCEPT"),
    (3, "image", 7, "ACCEPT"),
    (4, "image", 3, "ACCEPT"),
    (5, "image", 6, "ACCEPT"),
    (6, "image", 7, "ACCEPT"),
    (7, "image", 3, "ACCEPT"),
    (8, "image", 6, "ACCEPT"),
    (9, "image", 7, "ACCEPT"),
    (10, "image", 3, "ACCEPT");

INSERT INTO `process` (`process_id`, `enroll_id`, `lesson_id`, `created_at`, `status`)
VALUES
    (1, 1, 1, NOW(), 'In Progress'),
    (2, 2, 2, NOW(), 'In Progress'),
    (3, 3, 3, NOW(), 'In Progress'),
    (4, 4, 4, NOW(), 'In Progress'),
    (5, 5, 5, NOW(), 'In Progress'),
    (6, 6, 6, NOW(), 'In Progress'),
    (7, 7, 7, NOW(), 'In Progress'),
    (8, 8, 8, NOW(), 'In Progress'),
    (9, 9, 9, NOW(), 'In Progress'),
    (10, 10, 10, NOW(), 'Completed'),
    (11, 1, 2, NOW(), 'In Progress'),
    (12, 2, 12, NOW(), 'In Progress'),
    (13, 3, 3, NOW(), 'In Progress'),
    (14, 4, 4, NOW(), 'In Progress'),
    (15, 5, 5, NOW(), 'In Progress'),
    (16, 6, 1, NOW(), 'In Progress'),
    (17, 7, 2, NOW(), 'In Progress'),
    (18, 8, 3, NOW(), 'In Progress'),
    (19, 9, 4, NOW(), 'In Progress'),
    (20, 10, 12, NOW(), 'Completed');

INSERT INTO `orders` (`order_id`, `price`, `method`, `description`, `user_id`, `course_id`, `created_at`, `updated_at`, `status`)
VALUES
    (1, 100, 'Credit Card', 'Order for Course 1', 5, 1, NOW(), NOW(), 'Completed'),
    (2, 150, 'PayPal', 'Order for Course 2', 8, 2, NOW(), NOW(), 'Completed'),
    (3, 200, 'Credit Card', 'Order for Course 3', 9, 3, NOW(), NOW(), 'Completed'),
    (4, 120, 'PayPal', 'Order for Course 4', 10, 4, NOW(), NOW(), 'Completed'),
    (5, 180, 'Credit Card', 'Order for Course 5', 5, 5, NOW(), NOW(), 'Completed'),
    (6, 110, 'PayPal', 'Order for Course 1', 8, 1, NOW(), NOW(), 'Completed'),
    (7, 160, 'Credit Card', 'Order for Course 2', 9, 2, NOW(), NOW(), 'Completed'),
    (8, 210, 'PayPal', 'Order for Course 3', 10, 3, NOW(), NOW(), 'Completed'),
    (9, 130, 'Credit Card', 'Order for Course 4', 5, 4, NOW(), NOW(), 'Completed'),
    (10, 190, 'PayPal', 'Order for Course 5', 8, 5, NOW(), NOW(), 'Completed'),
    (11, 105, 'Credit Card', 'Order for Course 1', 10, 1, NOW(), NOW(), 'Completed'),
    (12, 155, 'PayPal', 'Order for Course 2', 9, 2, NOW(), NOW(), 'Completed'),
    (13, 215, 'Credit Card', 'Order for Course 3', 8, 3, NOW(), NOW(), 'Completed'),
    (14, 125, 'PayPal', 'Order for Course 4', 5, 4, NOW(), NOW(), 'Completed'),
    (15, 185, 'Credit Card', 'Order for Course 5', 10, 5, NOW(), NOW(), 'Completed'),
    (16, 120, 'Credit Card', 'Order for Course 1', 5, 1, NOW(), NOW(), 'Completed'),
    (17, 170, 'PayPal', 'Order for Course 2', 8, 2, NOW(), NOW(), 'Completed'),
    (18, 220, 'Credit Card', 'Order for Course 3', 9, 3, NOW(), NOW(), 'Completed');

INSERT INTO `drawcourses`.`assignment` (`assignment_id`, `assignment_title`, `topic`, `lesson_id`, `status`) VALUES ('1', 'a', 'animal', '1', 'Not Complete');


INSERT INTO `assignment` (`assignment_id`, `assignment_title`, `topic`, `created_at`, `updated_at`, `lesson_id`, `status`)
VALUES
    (11, 'Assignment 1', 'Introduction to SQL', NOW(), NOW(), 1, 'Active'),
    (2, 'Assignment 2', 'Data Types', NOW(), NOW(), 2, 'Active'),
    (3, 'Assignment 3', 'SQL Queries', NOW(), NOW(), 3, 'Active'),
    (4, 'Assignment 4', 'Database Design', NOW(), NOW(), 4, 'Active'),
    (5, 'Assignment 5', 'Joins and Subqueries', NOW(), NOW(), 5, 'Active'),
    (6, 'Assignment 6', 'Indexes and Optimization', NOW(), NOW(), 6, 'Active'),
    (7, 'Assignment 7', 'Transactions', NOW(), NOW(), 7, 'Active'),
    (8, 'Assignment 8', 'Stored Procedures', NOW(), NOW(), 8, 'Active'),
    (9, 'Assignment 9', 'Triggers', NOW(), NOW(), 9, 'Active'),
    (10, 'Assignment 10', 'Normalization', NOW(), NOW(), 10, 'Active');


INSERT INTO `user_assignment` (`enroll_id`, `assignment_id`, `task_title`, `description`, `image`, `status`, `grade`, `created_at`, `updated_at`)
VALUES
    (1, 11, 'Task 11', 'Write complex SQL queries', 'task11.jpg', 'Pending', NULL, NOW(), NOW()),
    (2, 1, 'Task 12', 'Optimize SQL performance', 'task12.jpg', 'Pending', NULL, NOW(), NOW()),
    (3, 1, 'Task 13', 'Complete the SQL assignment', 'task13.jpg', 'Pending', NULL, NOW(), NOW()),
    (4, 2, 'Task 14', 'Design a database schema', 'task14.jpg', 'Pending', NULL, NOW(), NOW()),
    (5, 3, 'Task 15', 'Write complex SQL queries', 'task15.jpg', 'Pending', NULL, NOW(), NOW()),
    (6, 4, 'Task 16', 'Optimize SQL performance', 'task16.jpg', 'Pending', NULL, NOW(), NOW()),
    (7, 5, 'Task 17', 'Create SQL joins and subqueries', 'task17.jpg', 'Pending', NULL, NOW(), NOW()),
    (8, 6, 'Task 18', 'Implement database indexes', 'task18.jpg', 'Pending', NULL, NOW(), NOW()),
    (9, 7, 'Task 19', 'Manage SQL transactions', 'task19.jpg', 'Pending', NULL, NOW(), NOW()),
    (10, 8, 'Task 20', 'Write stored procedures', 'task20.jpg', 'Pending', NULL, NOW(), NOW());

