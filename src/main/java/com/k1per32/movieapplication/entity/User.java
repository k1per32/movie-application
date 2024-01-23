package com.k1per32.movieapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "users", schema = "movie_application")
@Data
public class User implements Serializable {
    @Id
    @Column(name = "id_users")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String username;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "enabled")
    private boolean enabled;
    @ManyToMany
    @JoinTable(name = "favourites",
            joinColumns = @JoinColumn(name = "id_users_fav"),
            inverseJoinColumns = @JoinColumn(name = "id_movies_fav")
    )
    @JsonIgnore
    private List<Movie> movieList;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}