package alten.api.resources.users;

import alten.core.dtos.user.UserDTO;
import alten.core.dtos.user.UserUpdateDTO;
import alten.core.services.users.IUsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User resource implementation
 */
@RestController
@RequestMapping("/users")
@Tag(name = "Users API")
@AllArgsConstructor
@Slf4j
public class UsersResourceImpl implements IUsersResource{

    final IUsersService usersService;



    @Operation(summary = "Register new user")
    @PostMapping("/account")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        log.info("[API : registerUser : BEGIN]");
            UserDTO createdUser = usersService.registerUser(userUpdateDTO);
            log.info("[API : registerUser : END]");
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Update user information")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        log.info("[API : updateUser : BEGIN]");
            UserDTO updatedUser = usersService.updateUser(id, userUpdateDTO);
            log.info("[API : updateUser : END]");
            return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Get user by ID")
    @Override
    public ResponseEntity<UserDTO> getUser() {
        log.info("[API : getUser : BEGIN]");
        UserDTO userDTO = usersService.getUser();
        log.info("[API : getUser : END]");
        return ResponseEntity.ok(userDTO);
    }

}
