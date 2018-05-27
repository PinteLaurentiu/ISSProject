package com.iss.exceptions;

public class NotActivatedUserException extends LoginException {
    public NotActivatedUserException() {
        super("User was not activated!");
    }
}
