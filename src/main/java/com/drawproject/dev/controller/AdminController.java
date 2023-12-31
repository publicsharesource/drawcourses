package com.drawproject.dev.controller;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.*;
import com.drawproject.dev.model.*;
import com.drawproject.dev.repository.*;
import com.drawproject.dev.service.ContactService;
import com.drawproject.dev.service.OrderService;
import com.drawproject.dev.service.PostService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ContactService contactService;

    @Autowired
    PostService postService;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private OrderService orderService;

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
        String hashedPassword = passwordEncoder.encode(userDTO.getPwd());
        user.setPwd(hashedPassword);
        user.setMobileNum(userDTO.getMobileNum());
        user.setFullName(userDTO.getFullName());
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
        Pageable pageable = PageRequest.of(page - 1, perPage);
        Page<Posts> postPage = postRepository.findAll(pageable);

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
    @PutMapping("post/{id}")
    public ResponseEntity<String> closePost(@PathVariable int id){
            postService.updatePostStatus(id);
            return new ResponseEntity<>("Close post Successful", HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderAdminDTO>> showPaymentHistory(){
        List<Orders> orders = orderService.getAllOrder();
        List<OrderAdminDTO> orderAdminDTOs = new ArrayList<>();
        for(Orders order : orders){
            OrderAdminDTO orderAdminDTO = new OrderAdminDTO();
            orderAdminDTO.setUsername(order.getUser().getUsername());
            orderAdminDTO.setFullName(order.getUser().getFullName());
            orderAdminDTO.setCourseName(order.getCourse().getCourseTitle());
            orderAdminDTO.setStatus(order.getStatus());
            orderAdminDTO.setPrice(String.valueOf(order.getPrice())); // Convert price to string or format it as needed

            orderAdminDTOs.add(orderAdminDTO);
        }
        return ResponseEntity.ok(orderAdminDTOs);
    }


    @GetMapping("/contact")
    public List<Contact> displayMessages(){
        List<Contact> contactMsgs= contactService.findMsgsWithOpenStatus();
        return contactMsgs;
    }
    @PutMapping("contact/{id}")
    public ResponseEntity<String> closeMsg(@PathVariable int id){
        contactService.updateMsgStatus(id);
        return new ResponseEntity<>("Close Contact Successful", HttpStatus.OK);
    }
}
