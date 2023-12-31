package com.drawproject.dev.controller;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.PostDTO;
import com.drawproject.dev.dto.PostResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.model.Category;
import com.drawproject.dev.model.Posts;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.CategoryRepository;
import com.drawproject.dev.repository.PostRepository;
import com.drawproject.dev.repository.UserRepository;
import com.drawproject.dev.service.FileService;
import com.drawproject.dev.service.PostService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FileService fileService;

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> savePost(MultipartFile requestImage, @Valid PostDTO postDTO, Errors errors, Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if(errors.hasErrors()){
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);

        }
        Optional<Category> optionalCategory = categoryRepository.findById(postDTO.getCategoryId());
        if (optionalCategory.isPresent())
        {
            Category category = new Category();
            User user1 = new User();
            user1.setUserId(user.getUserId());
            category.setCategoryId(postDTO.getCategoryId());
            Posts posts = new Posts();
            posts.setCategory(category);
            posts.setImage(fileService.uploadFile(requestImage, user.getUserId(), "image", "posts"));
            posts.setDescription(postDTO.getDescription());
            posts.setReadingTime(postDTO.getReadingTime());
            posts.setStatus(DrawProjectConstaints.OPEN);
            posts.setTitle(postDTO.getTitle());
            posts.setBody(postDTO.getBody());
            posts.setUser(user1);
            postRepository.save(posts);
            return new ResponseEntity<>("Create post success", HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().body("Invalid category ID");
        }

    }
    @GetMapping
    public ResponseEntity<PostResponseDTO<PostDTO>> getPosts(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "perPage", defaultValue = "10") int perPage
    ) {
        Pageable pageable = PageRequest.of(page - 1, perPage); // Page numbers are 0-based
        Page<Posts> postPage = postRepository.findByStatus(DrawProjectConstaints.OPEN, pageable);

        List<PostDTO> postDTOList = postPage.getContent().stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        PostResponseDTO<PostDTO> response = new PostResponseDTO<>();
        response.setPage(page);
        response.setPer_page(perPage);
        response.setTotal((int) postPage.getTotalElements());
        response.setTotal_pages(postPage.getTotalPages());
        response.setData(postDTOList);

        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> closePost(@PathVariable int id, Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        Optional<Posts> posts = postRepository.findById(id);
        if (posts != null && posts.get().getUser().getUserId() == user.getUserId() || user.getRoles().getName().equals("ADMIN") || user.getRoles().getName().equals("STAFF")) {
            postService.updatePostStatus(id);
            return new ResponseEntity<>("Close post Successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("{postId}")
    public ResponseEntity<PostDTO> showPostDetail(@PathVariable int postId){
        Posts post = postRepository.findPostsByPostId(postId);
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(post.getTitle());
        postDTO.setAvatar(post.getUser().getAvatar());
        postDTO.setDescription(post.getDescription());
        postDTO.setBody(post.getBody());
        postDTO.setCategoryId(post.getCategory().getCategoryId());
        postDTO.setCategoryName(post.getCategory().getCategoryName());
        postDTO.setImage(post.getImage());
        postDTO.setReadingTime(post.getReadingTime());
        postDTO.setCreatedAt(post.getCreatedAt());
        postDTO.setUserId(post.getUser().getUserId());
        postDTO.setPostId(post.getPostId());
        postDTO.setUserName(post.getUser().getUsername());
        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponsePagingDTO> searchResponse(@RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "eachPage", defaultValue = "4") int eachPage,
                                                            @RequestParam(value = "search", defaultValue = "") String search,
                                                            @RequestParam(value = "categoryId", defaultValue = "0") int categoryId) {

        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok().body(postService.searchPosts(page, eachPage, search, categoryId));

    }

}
