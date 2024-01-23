package com.k1per32.movieapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "movies", schema = "movie_application")
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movies", nullable = false)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;
    @ManyToMany
    @JoinTable(name = "favourites",
            joinColumns = @JoinColumn(name = "id_movies_fav"),
            inverseJoinColumns = @JoinColumn(name = "id_users_fav")
    )
    @JsonIgnore
    private List<User> usersList;

    public Movie(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}