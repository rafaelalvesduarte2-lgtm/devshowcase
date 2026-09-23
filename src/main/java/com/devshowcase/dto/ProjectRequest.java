package com.devshowcase.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public class ProjectRequest {

    @NotBlank(message = "O título do projeto é obrigatório")
    private String title;

    private String description;

    @NotBlank(message = "A URL do projeto é obrigatória")
    @Pattern(
            regexp = "https?://.+",
            message = "A URL deve começar com http:// ou https://"
    )
    private String url;

    @NotNull(message = "O profileId é obrigatório")
    private Long profileId;

    private Set<Long> technologyIds;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Set<Long> getTechnologyIds() {
        return technologyIds;
    }

    public void setTechnologyIds(Set<Long> technologyIds) {
        this.technologyIds = technologyIds;
    }
}