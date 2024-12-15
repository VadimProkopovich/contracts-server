package tests.lyceum.apitests.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tests.lyceum.apitests.models.User;
import tests.lyceum.apitests.models.dto.UserDTO;
import tests.lyceum.apitests.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Tag(name = "User", description = "Operations pertaining to users in User Management System")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users", description = "Get a list of all users")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))})
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllDTO();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID", description = "Get a specific user by their ID")
    @ApiResponse(responseCode = "200", description = "User found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))})
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> user = userService.getDTOById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user")
    @ApiResponse(responseCode = "201", description = "User created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))})
    public ResponseEntity<Void> createUser(@RequestBody UserDTO userDTO) {
        userService.create(new User(userDTO));
        return ResponseEntity.status(201).body(null);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticate user by username and password")
    @ApiResponse(responseCode = "200", description = "User authenticated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))})
    @ApiResponse(responseCode = "401", description = "Authentication failed")
    public ResponseEntity<UserDTO> authenticateUser(@RequestBody UserDTO userDTO) {
        User user = userService.authenticate(userDTO.getEmail(), userDTO.getPassword());
        if (user != null) {
            UserDTO authenticatedUser = new UserDTO(user);
            return ResponseEntity.ok(authenticatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Update an existing user")
    @ApiResponse(responseCode = "200", description = "User updated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))})
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        UserDTO updatedUser = userService.update(id, updates);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Delete a user by their ID")
    @ApiResponse(responseCode = "204", description = "User deleted")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}