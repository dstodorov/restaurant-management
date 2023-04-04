package com.dst.restaurantmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Username already exist.")
public class UsernameDuplicationException extends RestaurantManagerException {
    public UsernameDuplicationException(String message) {
        super(message);
    }
}
