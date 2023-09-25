package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.map.MapFeedback;
import com.drawproject.dev.model.Feedback;
import com.drawproject.dev.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    public ResponseDTO getFeedback(int courseId, int page, int eachPage) {
        Pageable pageable = PageRequest.of(page, eachPage);
        Page<Feedback> feedbacks = feedbackRepository.findByCoursesCourseId(courseId, pageable);

        return new ResponseDTO(HttpStatus.OK, "Request successfully!", new ResponsePagingDTO(page, feedbacks.getTotalPages(), eachPage, MapFeedback.mapListFeedbackToDTO(feedbacks.getContent())));
    }

}