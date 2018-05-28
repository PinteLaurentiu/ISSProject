package com.iss.service;

import com.iss.domain.Spital;
import org.apache.catalina.Server;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class SpitalProxy implements ICrudService<Spital, Integer> {

    private final ProxyFactory parent;
    private String host;
    private RestTemplate restTemplate = new RestTemplate();

    public SpitalProxy(String host, ProxyFactory parent) {
        this.host = host + "/spitale";
        this.parent = parent;
    }


    @Override
    public void add(Object... objects) {

    }

    @Override
    public void remove(Integer key) {

    }

    @Override
    public void edit(Spital self) {

    }

    @Override
    public Iterable<Spital> getAll() {
        ResponseEntity<Spital[]> responseEntity = restTemplate.getForEntity(host, Spital[].class);
        if (responseEntity.getStatusCode() != HttpStatus.OK)
            throw new ServerException("getAll spitale exception");
        return Arrays.asList(responseEntity.getBody());
    }

    @Override
    public Iterable<Spital> getAll(int count, int offset) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }
}
