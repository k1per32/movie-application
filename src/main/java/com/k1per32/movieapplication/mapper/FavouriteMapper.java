package com.k1per32.movieapplication.mapper;

import com.k1per32.movieapplication.dto.FavouriteDto;
import com.k1per32.movieapplication.entity.Favourite;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FavouriteMapper {
    Favourite convertToFavourite(FavouriteDto favouriteDto);
    FavouriteDto convertToFavouriteDto(Favourite favourite);
}
