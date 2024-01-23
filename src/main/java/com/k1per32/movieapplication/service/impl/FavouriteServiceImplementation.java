package com.k1per32.movieapplication.service.impl;


import com.k1per32.movieapplication.dto.MovieDto;
import com.k1per32.movieapplication.entity.Favourite;
import com.k1per32.movieapplication.entity.Movie;
import com.k1per32.movieapplication.entity.User;
import com.k1per32.movieapplication.exception.AlreadyExistException;
import com.k1per32.movieapplication.mapper.MovieMapper;
import com.k1per32.movieapplication.repository.FavouriteRepository;
import com.k1per32.movieapplication.repository.MovieRepository;
import com.k1per32.movieapplication.repository.UserRepository;
import com.k1per32.movieapplication.service.FavouriteService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.QueryParam;
import java.util.*;
import java.util.stream.Collectors;


@Data
@Log4j2
@Service
public class FavouriteServiceImplementation implements FavouriteService {

    private final JdbcTemplate jdbcTemplate;
    private final MovieRepository movieRepository;
    private final FavouriteRepository favouriteRepository;
    private final UserRepository userRepository;
    private final MovieMapper movieMapper;

    @Override
    public List<MovieDto> getNotInFavourite(@PathVariable int id, @QueryParam("loaderType") String loaderType) {
        List<Movie> list = new ArrayList<>();
        if (loaderType.equalsIgnoreCase("sql")) {
            log.info("Вывод всех фильмов, которых у пользователя нет в избранном с помощью sql запроса");
            String sql = "SELECT * FROM movies WHERE NOT EXISTS (" +
                    "SELECT * FROM favourites WHERE id_movies_fav = movies.id_movies AND id_users_fav = ?)";
            list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Movie.class), id)
                    .stream().sorted(Comparator.comparing(Movie::getName)).collect(Collectors.toList());
            log.info("Вывод всех фильмов, которых у пользователя нет в избранном с помощью sql запроса завершен");
        }
        if (loaderType.equalsIgnoreCase("in memory")) {
            log.info("Вывод всех фильмов, которых у пользователя нет в избранном с помощью памяти приложения");
            List<Movie> movieList = movieRepository.findAll();
            List<Movie> movieListFromUsersOpt = userRepository.findById(id).get().getMovieList();
            movieListFromUsersOpt = movieListFromUsersOpt
                    .stream()
                    .sorted(Comparator.comparing(Movie::getId))
                    .collect(Collectors.toList());
            movieList.removeAll(movieListFromUsersOpt);
            list.addAll(movieList);
            log.info("Вывод всех фильмов, которых у пользователя нет в избранном с помощью памяти приложения ззавершен");
        }
        return list.stream().map(movieMapper::convertToMovieDto).collect(Collectors.toList());
    }

    @Override
    public String addFavourite(int id, String name) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            log.info("Добавление фильма в избранное");
            Optional<Movie> movieOptional = movieRepository.findByName(name);
            if (movieOptional.isPresent()) {
                Favourite favourite = new Favourite();
                favourite.setIdMoviesFav(movieOptional.get().getId());
                favourite.setIdUsersFav(userOptional.get().getId());
//                if (favouriteRepository.findAllByIdMoviesFav(favourite.getIdMoviesFav()).get().equals(favourite.getIdMoviesFav())
//                        && favouriteRepository.findAllByIdUsersFav(favourite.getIdUsersFav()).get().equals(favourite.getIdUsersFav())) {
//                    throw new AlreadyExistException("Этот фильм" + name + "уже находится в избранном");
//                }
                favouriteRepository.save(favourite);
                log.info("Добавление фильма в избранное завершено");
                return "Добавление фильма в избранное " + name + " завершено успешно";
            } else throw new NoSuchElementException("Название фильма: " + name + " введено неверно");
        } else throw new NoSuchElementException("Пользователя с данным id: " + id + " не существует");


    }

    @Override
    public String deleteFavourite(int id, String name) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            log.info("Удаление фильма из избранного");
            Optional<Movie> movieOptional = movieRepository.findByName(name);
            if (movieOptional.isPresent()) {
                Optional<Favourite> favourite = favouriteRepository.findAllByIdUsersFavAndIdMoviesFav(
                        userOptional.get().getId(),
                        movieOptional.get().getId());
                favourite.ifPresent(favouriteRepository::delete);
                log.info("Удаление фильма из избранного завершено успешно");
                return "Удаление фильма " + name + " из избранного завершено успешно";
            } else throw new NoSuchElementException("Название фильма: " + name + " введено неверно");
        } else throw new NoSuchElementException("Пользователя с данным id: " + id + " не существует");
    }
}