package com.k1per32.movieapplication.controller;

import com.k1per32.movieapplication.dto.RequestRegisterDto;
import com.k1per32.movieapplication.dto.UserDto;
import com.k1per32.movieapplication.dto.UserDtoWithoutEmail;
import com.k1per32.movieapplication.exception.AlreadyExistException;
import com.k1per32.movieapplication.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Data
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @Operation(
            summary = "Регистрация пользователей",
            tags = {"User service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered the new user", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))),
            @ApiResponse(responseCode = "409", description = "Email уже используется", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AlreadyExistException.class)))),
            @ApiResponse(responseCode = "400", description = "No valid data", content = @Content(array = @ArraySchema(schema = @Schema(implementation = MethodArgumentNotValidException.class)))),
    })
    @PostMapping("/register")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody RequestRegisterDto requestRegisterDto) {
        return ResponseEntity.ok(userService.signUp(requestRegisterDto));
    }

    @Operation(
            summary = "Получение списка всех пользователей",
            tags = {"User service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class)))),
    })
    @GetMapping
    public ResponseEntity<List<UserDto>> listUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
            summary = "Изменение данных пользователя",
            tags = {"User service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class)))),
            @ApiResponse(responseCode = "404", description = "No value present", content = @Content(array = @ArraySchema(schema = @Schema(implementation = NoSuchElementException.class)))),
            @ApiResponse(responseCode = "400", description = "No valid data", content = @Content(array = @ArraySchema(schema = @Schema(implementation = MethodArgumentNotValidException.class)))),
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> changeUser(@PathVariable int id, @Valid @RequestBody UserDtoWithoutEmail userDto) {
        return ResponseEntity.ok(userService.changeUser(id, userDto));
    }

    @Operation(
            summary = "Получение данных пользователя по его id",
            tags = {"User service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class)))),
            @ApiResponse(responseCode = "404", description = "No value present", content = @Content(array = @ArraySchema(schema = @Schema(implementation = NoSuchElementException.class)))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUsersById(id));
    }

    @Operation(
            summary = "Удаление пользователя",
            tags = {"User service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class)))),
            @ApiResponse(responseCode = "404", description = "No value present", content = @Content(array = @ArraySchema(schema = @Schema(implementation = NoSuchElementException.class)))),
    })
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

}
