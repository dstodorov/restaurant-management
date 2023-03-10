package com.dst.restaurantmanagement.exceptions;

public class UsernameDuplicationException extends RestaurantManagerException {
    public UsernameDuplicationException(String message) {
        super(message);
    }

    public UsernameDuplicationException() {
    }
}
