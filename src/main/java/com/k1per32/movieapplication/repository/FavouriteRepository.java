package com.k1per32.movieapplication.repository;

import com.k1per32.movieapplication.entity.Favourite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {
    Optional<Favourite>  findAllByIdUsersFavAndIdMoviesFav(int  idUsersFav, int idMoviesFav);

    Optional<Integer> findAllByIdUsersFav(int idUsersFav);

    Optional<Integer> findAllByIdMoviesFav(int idMoviesFav);
}
