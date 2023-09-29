package com.drawproject.dev.controller;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.PostDTO;
import com.drawproject.dev.dto.PostResponseDTO;
import com.drawproject.dev.dto.UserDTO;
import com.drawproject.dev.dto.UserResponseDTO;
import com.drawproject.dev.model.Posts;
import com.drawproject.dev.model.Roles;
import com.drawproject.dev.model.Skill;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.PostRepository;
import com.drawproject.dev.repository.RoleRepository;
import com.drawproject.dev.repository.SkillRepository;
import com.drawproject.dev.repository.UserRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/user")
    public ResponseEntity<List<UserResponseDTO>> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> userDTOs = users.stream()
                .map(user -> {
                    UserResponseDTO userDTO = new UserResponseDTO();
                    BeanUtils.copyProperties(user, userDTO); // Copy properties from User to UserDTO
                    return userDTO;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDTOs);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<String> disableUser(@PathVariable int userId){
        User user = userRepository.findUserByUserId(userId);
        user.setStatus(DrawProjectConstaints.CLOSE);
        userRepository.save(user);
        return new ResponseEntity<>("User Has Been Disable", HttpStatus.OK);
    }
    @PostMapping("/user")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO userDTO, Errors errors){
        if(errors.hasErrors()){
            return new ResponseEntity<>(errors.toString(),HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPwd(userDTO.getPwd());
        user.setMobileNum(userDTO.getMobileNum());
        user.setEmail(userDTO.getEmail());
        user.setStatus(DrawProjectConstaints.OPEN);
        Skill skill = skillRepository.findBySkillId(userDTO.getSkillId());
        user.setSkill(skill);
        if (userDTO.getRoleId() == 0) {
            Roles defaultRole = roleRepository.findById(4)
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            user.setRoles(defaultRole);
        } else {
            Roles userRole = roleRepository.findById(userDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRoles(userRole);
        }
        userRepository.save(user);
        return new ResponseEntity<>("Create User Success",HttpStatus.OK);
    }

    @GetMapping("/posts")
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
}
