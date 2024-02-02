package com.k1per32.movieapplication.service;

import com.k1per32.movieapplication.config.CustomUserDetails;
import com.k1per32.movieapplication.entity.User;
import com.k1per32.movieapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        return user.map(CustomUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException(username + " не существует"));
    }
}
