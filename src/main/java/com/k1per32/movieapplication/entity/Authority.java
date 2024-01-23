package com.k1per32.movieapplication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "authorities", schema = "movie_application")
@Data
public class Authority {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "authority")
    private String authority;
}
