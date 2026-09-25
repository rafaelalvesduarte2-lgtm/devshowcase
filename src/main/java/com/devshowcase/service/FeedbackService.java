package com.devshowcase.service;

import com.devshowcase.dto.FeedbackRequest;
import com.devshowcase.dto.FeedbackResponse;
import com.devshowcase.entity.Feedback;
import com.devshowcase.entity.Project;
import com.devshowcase.repository.FeedbackRepository;
import com.devshowcase.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ProjectRepository projectRepository;

    public FeedbackService(
            FeedbackRepository feedbackRepository,
            ProjectRepository projectRepository) {
        this.feedbackRepository = feedbackRepository;
        this.projectRepository = projectRepository;
    }

    public FeedbackResponse createForProject(
            Long projectId,
            FeedbackRequest request) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        Feedback feedback = new Feedback();
        feedback.setComment(request.getComment());
        feedback.setRating(request.getRating());
        feedback.setProject(project);

        Feedback savedFeedback = feedbackRepository.save(feedback);

        List<Feedback> feedbacks =
                feedbackRepository.findByProjectId(projectId);

        double average = feedbacks.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);

        project.setAverageRating(average);

        projectRepository.save(project);

        return new FeedbackResponse(
                savedFeedback.getId(),
                savedFeedback.getComment(),
                savedFeedback.getRating(),
                savedFeedback.getProject().getId()
        );
    }
}