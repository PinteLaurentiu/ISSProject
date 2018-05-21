package com.iss.service;

import com.iss.UI.Login;
import com.iss.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserProxy implements ICrudService<User, Integer> {
    private String host;
    private RestTemplate restTemplate = new RestTemplate();

    public UserProxy(String host) {
        this.host = host + "/users";
    }

    @Override
    public void add(Object... objects) {
        LocalDate date = (LocalDate) objects[2];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
        String formattedString = date.format(formatter) + " 00:00:00";
        try {
            ResponseEntity responseEntity = restTemplate.postForEntity(host, new String[]{(String) objects[0],
                    (String) objects[1], formattedString, (String) objects[3],
                    (String) objects[4], (String) objects[5], (String) objects[6],
                    (String) objects[7], (String) objects[8], (String) objects[9],
                    (String) objects[10], (String) objects[11]}, Object.class);
            if (responseEntity.getStatusCode() != HttpStatus.OK)
                throw new Exception();
        } catch (Exception ex){
            throw new ServerException("Register failed");
        }
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

        try {
            ResponseEntity<Long> response = restTemplate.postForEntity(host + "/login", new String[]{user, password}, Long.class);
            return response.getBody();
        }
        catch (HttpClientErrorException ex){
            if (ex.getRawStatusCode() == 403)
                throw new LoginException();
            else
                throw new ServerException("Login error");
        }
        catch (Exception ex) {
            throw new ServerException("Login error");
        }
    }
}
