package com.dst.restaurantmanagement.exceptions;

public class PhoneNumberDuplicationException extends RestaurantManagerException {
    public PhoneNumberDuplicationException(String message) {
        super(message);
    }

    public PhoneNumberDuplicationException() {
    }
}
