package com.devshowcase.service;

import com.devshowcase.dto.ProfileRequest;
import com.devshowcase.dto.ProfileResponse;
import com.devshowcase.entity.Profile;
import com.devshowcase.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ProfileResponse create(ProfileRequest request) {

        Profile profile = new Profile();

        profile.setName(request.getName());
        profile.setEmail(request.getEmail());
        profile.setBio(request.getBio());

        Profile savedProfile = profileRepository.save(profile);

        return new ProfileResponse(
                savedProfile.getId(),
                savedProfile.getName(),
                savedProfile.getEmail(),
                savedProfile.getBio()
        );
    }

    public ProfileResponse findById(Long id) {

        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        return new ProfileResponse(
                profile.getId(),
                profile.getName(),
                profile.getEmail(),
                profile.getBio()
        );
    }
}