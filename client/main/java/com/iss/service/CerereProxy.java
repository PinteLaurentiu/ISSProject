package com.iss.service;

import com.iss.domain.Cerere;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class CerereProxy implements ICrudService<Cerere, Integer> {
    private final ProxyFactory parent;
    private String host;
    private RestTemplate restTemplate = new RestTemplate();

    public CerereProxy(String host, ProxyFactory parent) {
        this.host = host + "/cerere";
        this.parent = parent;
    }

    @Override
    public void add(Object... objects) {
       try {
            ResponseEntity responseEntity;
            responseEntity = restTemplate.postForEntity(host, new String[]{
                    (String) objects[0],
                    (String) objects[1],
                    (String) objects[2],
                    (String) objects[3],
                    (String) objects[4]},
                    Object.class);
            if (responseEntity.getStatusCode() != HttpStatus.OK)
                throw new Exception();
        } catch (Exception ex){
            throw new ServerException("Cerere esuata!");
        }
    }

    @Override
    public void remove(Integer key) {
        try {
            restTemplate.delete(host + "/" + parent.getSessionId() + "/" + key);
        }catch (Exception ex){
            throw new ServerException("Stergere cerere esuata");
        }
    }

    @Override
    public void edit(Cerere self) {
    }

    @Override
    public Iterable<Cerere> getAll() {
        return null;
    }

    @Override
    public Iterable<Cerere> getAll(int count, int offset) {
        ResponseEntity<Cerere[]> responseEntity = restTemplate.postForEntity(host + "/getPaginated", new Long[]{(long) count, (long) offset, parent.getSessionId()}, Cerere[].class);
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
}
