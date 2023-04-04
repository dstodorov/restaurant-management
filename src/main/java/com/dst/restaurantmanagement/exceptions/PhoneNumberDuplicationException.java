package com.dst.restaurantmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Phone number already exist.")
public class PhoneNumberDuplicationException extends RestaurantManagerException {
    public PhoneNumberDuplicationException(String message) {
        super(message);
    }
}
