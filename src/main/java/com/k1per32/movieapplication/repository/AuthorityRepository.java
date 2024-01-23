package com.k1per32.movieapplication.repository;

import com.k1per32.movieapplication.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Optional<Authority> findFirstByUsername(String name);
}
