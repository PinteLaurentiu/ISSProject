package com.iss.service;

import com.iss.domain.Role;
import com.iss.domain.User;
import com.iss.service.ProxyFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class UserProxy implements ICrudService<User, Integer> {
    private final ProxyFactory parent;
    private String host;
    private RestTemplate restTemplate = new RestTemplate();

    public UserProxy(String host, ProxyFactory parent) {
        this.host = host + "/users";
        this.parent = parent;
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
        try {
            restTemplate.delete(host + "/" + parent.getSessionId() + "/" + key);
        }catch (Exception ex){
            throw new ServerException("delete failed");
        }
    }

    @Override
    public void edit(User self) {
        try {
            restTemplate.put(host + "/" + parent.getSessionId(), self);
        }catch (Exception ex){
            throw new ServerException("update failed");
        }
    }

    @Override
    public Iterable<User> getAll() {
        return null;
    }

    @Override
    public Iterable<User> getAll(int count, int offset) {
        ResponseEntity<User[]> responseEntity = restTemplate.postForEntity(host + "/getPaginated", new Long[]{(long) count, (long) offset, parent.getSessionId()}, User[].class);
        if (responseEntity.getStatusCode() != HttpStatus.OK){
            throw new ServerException("getPaginated failed");
        }
        return Arrays.asList(responseEntity.getBody());
    }

    @Override
    public int count() {
        ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(host, Integer.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK){
            throw new ServerException("count failed");
        }
        return responseEntity.getBody();
    }

    public void login(String user, String password) throws LoginException {

        try {
            ResponseEntity<Long> response = restTemplate.postForEntity(host + "/login", new String[]{user, password}, Long.class);
            parent.setSessionId(response.getBody());
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

    public Iterable<Role> getRoles(){
        ResponseEntity<Role[]> responseEntity = restTemplate.postForEntity(host + "/roles", parent.getSessionId(), Role[].class);
        if (responseEntity.getStatusCode() != HttpStatus.OK){
            throw new ServerException("Couldn't get the roles!");
        }

        Role[] roles = responseEntity.getBody();
        return Arrays.asList(roles);
    }
}
