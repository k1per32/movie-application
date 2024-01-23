package com.k1per32.movieapplication.mapper;


import com.k1per32.movieapplication.dto.MovieDto;
import com.k1per32.movieapplication.entity.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    Movie convertToMovie(MovieDto movieDto);
    MovieDto convertToMovieDto(Movie movie);
}
