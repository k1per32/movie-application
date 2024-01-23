package com.k1per32.movieapplication;


import com.k1per32.movieapplication.entity.Movie;
import com.k1per32.movieapplication.entity.User;
import com.k1per32.movieapplication.repository.MovieRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.k1per32.movieapplication.repository.UserRepository;
import com.k1per32.movieapplication.scheduling.MovieSchedulingController;
import com.k1per32.movieapplication.service.MovieSchedulingService;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class MovieApplicationTests {


    @MockBean
    private UserRepository userRepository;


    @Autowired
    private MovieRepository movieRepository;

    @SpyBean
    private MovieSchedulingController movieSchedulingController;


    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
    }

    @Test
    public void testScheduledExecution() {
        movieSchedulingController.getMovie();
        Awaitility.await().atMost(1, TimeUnit.SECONDS).untilAsserted(() ->
                verify(movieSchedulingController, atLeastOnce()).getMovie()
        );
    }

    @Test
    public void testSaveMovies() {
        Assertions.assertFalse(movieRepository.findById(1).isEmpty());
        Assertions.assertFalse(movieRepository.findById(75).isEmpty());
    }

//    @BeforeAll
//    public void createListUser() {
//        List<User> userList = new ArrayList<>();
//        userList.add(new User());
//    }

//    @Test
//    void testGetAllCountries() throws Exception {
//        when(userRepository.findAll()).thenReturn();
//        mvc.perform(get("/countries"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(3)));
//        verify(countryRepository, times(1)).findAll();
//    }
}
