package com.k1per32.movieapplication.service;


import com.k1per32.movieapplication.dto.MovieDto;


import java.util.List;

public interface FavouriteService {
    List<MovieDto> getNotInFavourite(int id, String loaderType);
    String addFavourite(int id, String name);
    String deleteFavourite(int id, String name);
}