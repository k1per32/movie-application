package com.k1per32.movieapplication.controller;

import com.k1per32.movieapplication.dto.MovieDto;
import com.k1per32.movieapplication.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    @Operation(
            summary = "Получение первых 15 страниц фильмов из бд",
            tags = {"Movie service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class)))),
            @ApiResponse(responseCode = "400", description = "Index must not be 0 or lower", content = @Content(array = @ArraySchema(schema = @Schema(implementation = IllegalArgumentException.class)))),
    })
    @GetMapping("/{page}")
    public ResponseEntity<List<MovieDto>> getAllMovie(@PathVariable(name = "page") int page) {
        return ResponseEntity.ok(movieService.getAllMovie(page));
    }
}

