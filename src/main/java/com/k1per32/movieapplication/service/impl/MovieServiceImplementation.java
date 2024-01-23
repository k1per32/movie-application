package com.k1per32.movieapplication.service.impl;

import com.k1per32.movieapplication.dto.MovieDto;
import com.k1per32.movieapplication.entity.Movie;
import com.k1per32.movieapplication.mapper.MovieMapper;
import com.k1per32.movieapplication.repository.MovieRepository;
import com.k1per32.movieapplication.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MovieServiceImplementation implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public List<MovieDto> getAllMovie(int page) {
        if(page<0){
            throw new IllegalArgumentException("Индекс должен быть больше 0");
        }
        log.info("Получение " + page + " страницы фильмов из бд");
        return movieRepository.findAll(PageRequest.of(page - 1, 15))
                .map(movieMapper::convertToMovieDto)
                .stream()
                .collect(Collectors.toList());
    }
}
