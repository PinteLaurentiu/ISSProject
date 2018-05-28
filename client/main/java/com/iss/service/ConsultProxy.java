package com.iss.service;

import com.iss.domain.Consult;
import com.iss.domain.Donare;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;

public class ConsultProxy implements ICrudService<Consult, Integer> {

    private final ProxyFactory parent;
    private String host;
    private RestTemplate restTemplate = new RestTemplate();

    public ConsultProxy(String host, ProxyFactory parent) {
        this.host = host + "/consulturi";
        this.parent = parent;
    }

    @Override
    public void add(Object... objects) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(host, new String[]{objects[0].toString(),
                objects[1].toString(),
                objects[2].toString(),
                objects[3].toString(),
                objects[4].toString(),
                objects[5].toString(),
                objects[6].toString(),
                parent.getSessionId().toString()}, String.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw  new  ServerException("Add donare failed!!");
        }
    }

    @Override
    public void remove(Integer key) {

    }

    @Override
    public void edit(Consult self) {

    }

    @Override
    public Iterable<Consult> getAll() {
        ResponseEntity<Consult[]> responseEntity = restTemplate.getForEntity(host, Consult[].class);
        if (responseEntity.getStatusCode() != HttpStatus.OK)
            throw new ServerException("getAll consult exception");
        return Arrays.asList(responseEntity.getBody());

    }

    @Override
    public Iterable<Consult> getAll(int count, int offset) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }
}
