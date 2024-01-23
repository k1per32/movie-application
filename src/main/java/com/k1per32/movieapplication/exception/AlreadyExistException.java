package com.k1per32.movieapplication.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlreadyExistException extends RuntimeException {
    private String message;

}
