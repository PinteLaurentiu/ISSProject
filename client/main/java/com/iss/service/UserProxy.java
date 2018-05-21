package com.iss.service;

import com.iss.UI.Login;
import com.iss.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.LoginException;

public class UserProxy implements ICrudService<User, Integer> {
    private String host;

    public UserProxy(String host) {
        this.host = host + "/users";
    }

    @Override
    public void add(Object... objects) {

    }

    @Override
    public void remove(Integer key) {

    }

    @Override
    public void edit(User self) {

    }

    @Override
    public Iterable<User> getAll() {
        return null;
    }

    public Long login(String user, String password) throws LoginException {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Long> response = restTemplate.postForEntity(host + "/login", new String[]{user, password}, Long.class);
            if (response.getStatusCode() != HttpStatus.OK)
                throw new LoginException();
            return response.getBody();
        }
        catch (Exception ex){
            throw new LoginException();
        }
    }
}
