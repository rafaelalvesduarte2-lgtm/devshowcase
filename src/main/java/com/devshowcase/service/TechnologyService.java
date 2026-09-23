package com.devshowcase.service;

import com.devshowcase.dto.TechnologyRequest;
import com.devshowcase.dto.TechnologyResponse;
import com.devshowcase.entity.Technology;
import com.devshowcase.repository.TechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {

    private final TechnologyRepository technologyRepository;

    public TechnologyService(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public TechnologyResponse create(TechnologyRequest request) {

        Technology technology = new Technology();

        technology.setName(request.getName());

        Technology savedTechnology = technologyRepository.save(technology);

        return new TechnologyResponse(
                savedTechnology.getId(),
                savedTechnology.getName()
        );
    }

    public List<TechnologyResponse> findAll() {

        return technologyRepository.findAll()
                .stream()
                .map(technology -> new TechnologyResponse(
                        technology.getId(),
                        technology.getName()
                ))
                .toList();
    }
}