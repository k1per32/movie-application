package com.k1per32.movieapplication.service;

import com.k1per32.movieapplication.dto.MovieDto;
import com.k1per32.movieapplication.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {
    List<MovieDto> getAllMovie(int page);
}