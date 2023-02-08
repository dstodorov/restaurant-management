package com.dst.administrationservice.web;

import com.dst.administrationservice.dto.CreateUserRequest;
import com.dst.administrationservice.dto.UserInfoResponse;
import com.dst.administrationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfoResponse> getBookById(@PathVariable("id") Long userId) {
        UserInfoResponse userInfoResponse = this.userService.getEmployeeById(userId);

        if (userInfoResponse.getUsername().isEmpty()) {
            return ResponseEntity.
                    notFound().
                    build();
        } else {
            return ResponseEntity.
                    ok(userInfoResponse);
        }
    }

    @PostMapping
    public ResponseEntity<UserInfoResponse> createUser(@RequestBody CreateUserRequest employeeDTO,
                                                       UriComponentsBuilder uriComponentsBuilder) {
        UserInfoResponse userInfoResponse = this.userService.saveEmployee(employeeDTO);

        return ResponseEntity.
                created(uriComponentsBuilder
                        .path("/api/v1/user/{id}")
                        .build(userInfoResponse.getId()))
                .body(userInfoResponse);
    }
}