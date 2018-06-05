package com.iss.service;

import com.iss.domain.Donare;
import com.iss.domain.PungaSange;
import com.iss.domain.Spital;
import com.iss.enums.GrupaSange;
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

    public Iterable<Donare> getAllByUser(){
        ResponseEntity<Donare[]> responseEntity = restTemplate.getForEntity(host + "/" + parent.getSessionId().toString(),Donare[].class);
        if (responseEntity.getStatusCode() != HttpStatus.OK)
            throw new ServerException("getAllByUser donari exception");
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

    public void finalizare(Donare donare) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(host + "/finalizare",new String[]{parent.getSessionId().toString(),
                                                                            String.valueOf(donare.getId())}, String.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK)
            throw new ServerException("finalizare donari exception");
    }

    public void  addTransfer(PungaSange pungaSange, String to) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(host + "/transfer",new String[]{parent.getSessionId().toString(),
                String.valueOf(pungaSange.getIdSange()), to}, String.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK)
            throw new ServerException("addTransfer donari exception");
    }

    public void addAnaliza(Donare donare, String boli, String imunoH, GrupaSange grupaSange) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(host + "/analiza",new String[]{parent.getSessionId().toString(),
                String.valueOf(donare.getId()), boli, imunoH, grupaSange.toString()}, String.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK)
            throw new ServerException("addAnaliza donari exception");
    }

    public void addComponente(Donare donare) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(host +"/componente", new String[]{String.valueOf(donare.getId()),
                parent.getSessionId().toString()},String.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK)
            throw new ServerException("addAnaliza donari exception");
    }
}
