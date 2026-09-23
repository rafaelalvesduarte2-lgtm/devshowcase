package com.devshowcase.controller;

import com.devshowcase.dto.FeedbackRequest;
import com.devshowcase.dto.FeedbackResponse;
import com.devshowcase.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FeedbackResponse create(
            @Valid @RequestBody FeedbackRequest request) {

        return feedbackService.create(request);
    }
}