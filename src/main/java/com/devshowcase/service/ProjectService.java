package com.devshowcase.service;

import com.devshowcase.dto.ProjectRequest;
import com.devshowcase.dto.ProjectResponse;
import com.devshowcase.entity.Project;
import com.devshowcase.entity.Profile;
import com.devshowcase.entity.Technology;
import com.devshowcase.repository.ProjectRepository;
import com.devshowcase.repository.ProfileRepository;
import com.devshowcase.repository.TechnologyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProfileRepository profileRepository;
    private final TechnologyRepository technologyRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            ProfileRepository profileRepository,
            TechnologyRepository technologyRepository) {

        this.projectRepository = projectRepository;
        this.profileRepository = profileRepository;
        this.technologyRepository = technologyRepository;
    }

    public ProjectResponse create(ProjectRequest request) {

        Profile profile = profileRepository.findById(request.getProfileId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        Project project = new Project();

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setUrl(request.getUrl());
        project.setProfile(profile);

        if (request.getTechnologyIds() != null
                && !request.getTechnologyIds().isEmpty()) {

            Set<Technology> technologies =
                    technologyRepository.findAllById(request.getTechnologyIds())
                            .stream()
                            .collect(Collectors.toSet());

            project.setTechnologies(technologies);
        }

        Project savedProject = projectRepository.save(project);

        return toResponse(savedProject);
    }

    public List<ProjectResponse> findAll() {

        return projectRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Page<ProjectResponse> findAll(
            Long technologyId,
            int page,
            int size) {

        PageRequest pageable = PageRequest.of(page, size);

        Page<Project> projects;

        if (technologyId != null) {
            projects = projectRepository
                    .findByTechnologies_Id(technologyId, pageable);
        } else {
            projects = projectRepository.findAll(pageable);
        }

        return projects.map(this::toResponse);
    }

    public ProjectResponse upvote(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        if (project.getUpvotes() == null) {
            project.setUpvotes(1);
        } else {
            project.setUpvotes(project.getUpvotes() + 1);
        }

        Project updatedProject = projectRepository.save(project);

        return toResponse(updatedProject);
    }

    private ProjectResponse toResponse(Project project) {

        Long profileId = null;

        if (project.getProfile() != null) {
            profileId = project.getProfile().getId();
        }

        Set<Long> technologyIds = project.getTechnologies()
                .stream()
                .map(Technology::getId)
                .collect(Collectors.toSet());

        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getUrl(),
                profileId,
                technologyIds,
                project.getAverageRating() == null
                        ? 0.0
                        : project.getAverageRating(),
                project.getUpvotes() == null
                        ? 0
                        : project.getUpvotes()
        );
    }
}