package com.devshowcase.dto;

import jakarta.validation.constraints.NotBlank;

public class TechnologyRequest {

    @NotBlank(message = "O nome da tecnologia é obrigatório")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}