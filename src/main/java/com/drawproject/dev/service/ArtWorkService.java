package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.ArtWorkDTO;
import com.drawproject.dev.dto.Mail;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.map.MapArtWork;
import com.drawproject.dev.model.Artwork;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.ArtworkRepository;
import com.drawproject.dev.repository.CategoryRepository;
import com.drawproject.dev.repository.InstructorRepository;
import com.drawproject.dev.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Service
public class ArtWorkService {

    @Autowired
    ArtworkRepository artworkRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    FileService fileService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    public ResponsePagingDTO getArtworks(int page, int eachPage, int instructorId, int categoryId, String status) {

        Pageable pageable = PageRequest.of(page - 1, eachPage);
        Page<Artwork> artworks;

        if(categoryId != 0) {
            artworks = artworkRepository.findByInstructorInstructorIdAndCategoryCategoryIdAndStatus(instructorId, categoryId, status, pageable);
        } else if(!status.equalsIgnoreCase("")) {
            artworks = artworkRepository.findByInstructorInstructorIdAndStatus(instructorId, status, pageable);
        } else {
            artworks = artworkRepository.findByInstructorInstructorId(instructorId, pageable);
        }

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.FOUND, "Artwork found",
                artworks.getTotalElements(), page, artworks.getTotalPages(), eachPage, MapArtWork.mapArtWorkToDTOs(artworks.getContent()));

        if(artworks.isEmpty()) {
            responsePagingDTO.setStatus(HttpStatus.NOT_FOUND);
            responsePagingDTO.setMessage("Instruction might not uploaded any artwork yet");
        }

        return responsePagingDTO;
    }

    public ResponseDTO createArtwork(MultipartFile requestImage, ArtWorkDTO artWorkDTO, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if(!user.getRoles().getName().equals(DrawProjectConstaints.INSTRUCTOR)) {
            return new ResponseDTO(HttpStatus.METHOD_NOT_ALLOWED, "You are not an instructor", null);
        }
        Artwork artwork = new Artwork();
        artwork.setInstructor(instructorRepository.findById(user.getUserId()).orElseThrow());
        artwork.setImage(fileService.uploadFile(requestImage, user.getUserId(), "image", "artworks"));
        artwork.setCategory(categoryRepository.findById(artWorkDTO.getCategoryId()).orElseThrow());
        artwork.setStatus(DrawProjectConstaints.OPEN);
        artworkRepository.save(artwork);

        return new ResponseDTO(HttpStatus.CREATED, "Artwork created", "Your artwork will be reviewed by our");
    }

    public ResponseDTO acceptArtWork(String message, int artWorkId) {
        Artwork artwork = artworkRepository.findById(artWorkId).orElseThrow();
        artwork.setStatus(DrawProjectConstaints.OPEN);
        artworkRepository.save(artwork);

        return new ResponseDTO(HttpStatus.OK, "Artwork checked", "");
    }

    public ResponseDTO rejectArtWork(String message, int artWorkId) {
        Artwork artwork = artworkRepository.findById(artWorkId).orElseThrow();
        artwork.setStatus(DrawProjectConstaints.CLOSE);
        artworkRepository.save(artwork);

        return new ResponseDTO(HttpStatus.OK, "Artwork checked", message);
    }

    public ResponseDTO deleteArtWork(int artWorkId) {
        Artwork artwork = artworkRepository.findById(artWorkId).orElseThrow();
        artwork.setStatus(DrawProjectConstaints.CLOSE);
        artworkRepository.save(artwork);

        return new ResponseDTO(HttpStatus.OK, "Artwork deleted successfully!", "");
    }

    public ResponsePagingDTO viewArtWork(int page, int eachPage, String status) {
        Pageable pageable = PageRequest.of(page - 1, eachPage);
        Page<Artwork> artworks;
        if(status == null) {
            artworks = artworkRepository.findAll(pageable);
        } else {
            artworks = artworkRepository.findByStatus(status, pageable);
        }

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.FOUND, "Artwork found",
                artworks.getTotalElements(), page, artworks.getTotalPages(), eachPage, MapArtWork.mapArtWorkToDTOs(artworks.getContent()));

        if(artworks.isEmpty()) {
            responsePagingDTO.setStatus(HttpStatus.NOT_FOUND);
            responsePagingDTO.setMessage("ArtWorks is empty!");
        }

        return responsePagingDTO;
    }

    public ResponseDTO completeCheckArtWorks(int instructorId, String message) {
        User user = userRepository.findById(instructorId).orElseThrow();
        Mail mail = new Mail(user.getEmail(), DrawProjectConstaints.TEMPLATE_CHECK_COMPLETE);
        if(!user.getRoles().getName().equalsIgnoreCase(DrawProjectConstaints.INSTRUCTOR)) {
            return new ResponseDTO(HttpStatus.METHOD_NOT_ALLOWED, "You are not an instructor", "Email not sent");
        }
        mailService.sendMessage(mail, new HashMap<String, Object>() {
            {
                put("fullName", user.getFullName());
                put("typeNotification", "Artworks");
                put("message", message);
            }
        });
        return new ResponseDTO(HttpStatus.OK, "Artwork checked", "Send email to " + user.getEmail() + " Successfully");
    }

}
