package com.devshowcase.service;

import com.devshowcase.dto.ProjectRequest;
import com.devshowcase.dto.ProjectResponse;
import com.devshowcase.entity.Profile;
import com.devshowcase.entity.Project;
import com.devshowcase.entity.Technology;
import com.devshowcase.repository.ProfileRepository;
import com.devshowcase.repository.ProjectRepository;
import com.devshowcase.repository.TechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        Set<Technology> technologies = new HashSet<>();

        if (request.getTechnologyIds() != null) {
            technologies = new HashSet<>(
                    technologyRepository.findAllById(request.getTechnologyIds())
            );
        }

        Project project = new Project();

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setUrl(request.getUrl());
        project.setProfile(profile);
        project.setTechnologies(technologies);

        Project savedProject = projectRepository.save(project);

        return toResponse(savedProject);
    }

    public List<ProjectResponse> findAll() {

        return projectRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ProjectResponse toResponse(Project project) {

        Set<Long> technologyIds = project.getTechnologies()
                .stream()
                .map(Technology::getId)
                .collect(java.util.stream.Collectors.toSet());

        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getUrl(),
                project.getProfile().getId(),
                technologyIds
        );
    }
}