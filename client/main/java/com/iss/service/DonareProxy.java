package com.iss.service;

import com.iss.domain.Donare;
import com.iss.domain.Spital;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;

public class DonareProxy implements ICrudService<Donare, Integer> {

    private final ProxyFactory parent;
    private String host;
    private RestTemplate restTemplate = new RestTemplate();

    public DonareProxy(String host, ProxyFactory parent) {
        this.host = host + "/donari";
        this.parent = parent;
    }
    @Override
    public void add(Object... objects) {
        Long date = ((Date)objects[1]).getTime();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(host, new String[]{objects[0].toString(),
                date.toString(),
                objects[2].toString(),
                objects[3].toString(),
                parent.getSessionId().toString()}, String.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw  new  ServerException("Add donare failed!!");
        }
    }

    @Override
    public void remove(Integer key) {

    }

    @Override
    public void edit(Donare self) {

    }

    @Override
    public Iterable<Donare> getAll() {
        ResponseEntity<Donare[]> responseEntity = restTemplate.getForEntity(host, Donare[].class);
        if (responseEntity.getStatusCode() != HttpStatus.OK)
            throw new ServerException("getAll donari exception");
        return Arrays.asList(responseEntity.getBody());

    }

    @Override
    public Iterable<Donare> getAll(int count, int offset) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }
}
