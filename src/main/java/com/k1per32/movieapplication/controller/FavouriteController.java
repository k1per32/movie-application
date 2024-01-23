package com.k1per32.movieapplication.controller;


import com.k1per32.movieapplication.dto.MovieDto;

import com.k1per32.movieapplication.service.FavouriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favourite")
public class FavouriteController {

    private final FavouriteService favouriteService;

    @Operation(
            summary = "Вывод всех фильмов, которых у пользователя нет в избранном: " +
                    "реализовано 2 сервиса по поиску фильмов которых нет в избранном",
            tags = {"Favourites service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class)))),

    })
    @GetMapping("/{id}")
    public ResponseEntity<List<MovieDto>> getNotInFavourites(@PathVariable(name = "id") final int id,
                                                             @QueryParam("loaderType") String loaderType) {
        return ResponseEntity.ok(favouriteService.getNotInFavourite(id, loaderType));
    }

    @Operation(
            summary = "Добавление фильмов в избранное",
            tags = {"Favourites service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class)))),

    })
    @PostMapping("/{id}")
    public ResponseEntity<String> addFavourites(@PathVariable(name = "id") final int id,
                                                @RequestBody String name) {
        return ResponseEntity.ok(favouriteService.addFavourite(id, name));
    }


    @Operation(
            summary = "Удаление фильмов из избранного",
            tags = {"Favourites service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class)))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFavourites(@PathVariable(name = "id") final int id,
                                                   @RequestBody String name) {
        return ResponseEntity.ok(favouriteService.deleteFavourite(id, name));

    }
}
