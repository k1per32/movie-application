package com.k1per32.movieapplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDtoWithoutEmail {
    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Username must contain only Latin characters")
    private String username;

    private String name;
}
