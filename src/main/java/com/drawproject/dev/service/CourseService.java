package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.CourseDTO;
import com.drawproject.dev.dto.course.CoursePreviewDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.map.MapModel;
import com.drawproject.dev.model.Category;
import com.drawproject.dev.model.Courses;
import com.drawproject.dev.model.Skill;
import com.drawproject.dev.repository.CategoryRepository;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.repository.SkillRepository;
import com.drawproject.dev.repository.StyleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Course service.
 */
@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    StyleRepository styleRepository;

    @Autowired
    ModelMapper modelMapper;

    /**
     * Gets top course by category.
     *
     * @return top list course preview DTO by category
     */
    public ResponseDTO getTopCourseByCategory(int limit) {
        Map<Integer, List<CoursePreviewDTO>> list = new HashMap<>();

        categoryRepository.findAll().forEach(category ->
                list.put(category.getCategoryId(), MapModel.mapListToDTO(
                        courseRepository.findTopCourseByCategory
                                (category.getCategoryId(), limit))));

        return new ResponseDTO(HttpStatus.OK, "Request Successfully!", list);
    }

    public ResponseDTO searchCourse(int page, int eachPage, Integer star,
                                    List<Integer> categories, List<Integer> skills, String search) {

        Pageable pageable = PageRequest.of(page, eachPage);

        /*
                List<Integer> courseSearch = courseRepository
                .findByInformationContainingOrDescriptionContainingOrCourseTitleContaining(search, search, search, pageable)
                .getContent().stream()
                .map(Courses::getCourseId).toList();

        Page<Courses> result = courseRepository.findByStatusAndCourseIdIn(DrawProjectConstaints.OPEN, courseSearch, pageable);

        if(categories != null) {
            courseSearch = result.getContent().stream()
                    .map(Courses::getCourseId).toList();
            result = courseRepository.findByCourseIdInAndCategory_CategoryIdIn(courseSearch, categories, pageable);
        }
        if(skills != null) {
            courseSearch = result.getContent().stream()
                    .map(Courses::getCourseId).toList();
            result = courseRepository.findByCourseIdInAndSkillSkillIdIn(courseSearch, skills, pageable);
        }
        if (star != null) {
            courseSearch = result.getContent().stream()
                    .map(Courses::getCourseId).toList();
            result = courseRepository.findByAverageStar(courseSearch, star, pageable);
        }

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(page, result.getTotalPages(), eachPage, MapModel.mapListToDTO(result.getContent()));
        */


        if (categories == null) {
            categories = categoryRepository.findAll().stream().map(Category::getCategoryId).toList();
        }
        if (skills == null) {
            skills = skillRepository.findAll().stream().map(Skill::getSkillId).toList();
        }
        Page<Courses> courses = courseRepository.searchCourse(categories, skills, search, star, pageable);
        int totalPage = courses.getTotalPages();

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(page, totalPage, eachPage, MapModel.mapListCourseDetailsToDTO(courses));

        return new ResponseDTO(HttpStatus.OK, "Request Successfully", responsePagingDTO);
    }

    public ResponseDTO saveCourse(CourseDTO courseDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        Courses course;

        if (courseDTO.getCourseId() == 0) {
            course = new Courses();
            responseDTO.setMessage("Create new Courses Successfully");
        } else {
            course = courseRepository.findById(courseDTO.getCourseId()).orElseThrow();
            responseDTO.setMessage("Update Course Successfully");
        }
        modelMapper.map(courseDTO, course);

        try {
            course.setImage(courseDTO.getImage().getBytes());
            responseDTO.setStatus(HttpStatus.OK);
            responseDTO.setData(courseRepository.save(course));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return responseDTO;
    }

    public ResponseDTO getCourseDetailsById(int courseId) {

        Courses course = courseRepository.findById(courseId).orElseThrow();

        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK, "FOUND COURSE", MapModel.mapCourseDetailsToDTO(course));

        return responseDTO;
    }
}
