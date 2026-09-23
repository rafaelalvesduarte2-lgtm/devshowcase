package com.devshowcase.dto;

public class FeedbackResponse {

    private Long id;
    private String comment;
    private Integer rating;
    private Long projectId;

    public FeedbackResponse(
            Long id,
            String comment,
            Integer rating,
            Long projectId) {

        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public Integer getRating() {
        return rating;
    }

    public Long getProjectId() {
        return projectId;
    }
}