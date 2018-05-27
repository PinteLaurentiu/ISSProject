package com.iss.exceptions;

public class BadAuthenticationException extends LoginException {
    public BadAuthenticationException() {
        super("Wrong email, username or password!");
    }
}
