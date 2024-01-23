package com.k1per32.movieapplication.service.impl;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.k1per32.movieapplication.dto.MovieDto;
import com.k1per32.movieapplication.entity.Movie;
import com.k1per32.movieapplication.mapper.MovieMapper;
import com.k1per32.movieapplication.repository.MovieRepository;
import com.k1per32.movieapplication.service.MovieSchedulingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieSchedulingServiceImplementation implements MovieSchedulingService {
    private StringBuilder URL = new StringBuilder(
            "https://api.kinopoisk.dev/v1.4/movie?page=1&limit=75&selectFields=name&selectFields=poster&notNullFields=name&notNullFields=poster.url");

    private final MovieRepository movieRepository;

    private final String X_API_KEY = "T0QB76K-2T0M9PD-MNZS537-MH61YX2";

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

    private static String result;

    private static MovieDto movieDto;

    private final MovieMapper movieMapper;

    public MovieDto getMovie() {

        log.info("The request to receive movies from the Kinopoisk API begins");

        HttpGet request = new HttpGet(URL.toString());

        // add request headers
        request.addHeader("X-API-KEY", X_API_KEY);

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
//            Header headers = entity.getContentType();

            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
            log.info("Request received successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scheduler.execute(this::save);

        return movieDto;
    }

    private void save() {
        log.info("Begin to save movie in db and create dto thread: " + Thread.currentThread().getName());
        JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
        JsonArray docs = jsonObject.get("docs").getAsJsonArray();
        for (int i = 0; i < docs.size(); i++) {
            JsonObject members = docs.get(i).getAsJsonObject();
            Movie movie = new Movie();
            movie.setName(members.getAsJsonPrimitive("name").getAsString());
            movie.setUrl(members.getAsJsonObject("poster")
                    .getAsJsonPrimitive("url").getAsString());
            if (movieRepository.findByName(members.getAsJsonPrimitive("name").getAsString()).isEmpty()) {
                movieRepository.save(movie);
                movieDto = movieMapper.convertToMovieDto(movie);
                log.info("Finish to save " + movie.getName() + " in db and create dto thread: " + Thread.currentThread().getName());
            }
        }
        Thread.currentThread().interrupt();
    }
}