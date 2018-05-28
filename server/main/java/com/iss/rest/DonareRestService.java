package com.iss.rest;

import com.iss.domain.Donare;
import com.iss.domain.DonareStatus;
import com.iss.domain.Role;
import com.iss.domain.Spital;
import com.iss.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/donari")
public class DonareRestService {
    private final ServiceFactory factory;

    @Autowired
    public DonareRestService(ServiceFactory factory) {
        this.factory = factory;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody String[] strings){
        if (strings.length != 5){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Spital spital = factory.get(SpitalService.class).getByNume(strings[0]);
        Date date = new Date(Long.valueOf(strings[1]));
        String pentru = strings[2];
        DonareStatus status = DonareStatus.valueOf(strings[3]);
        Long sessionId = Long.valueOf(strings[4]);
        if (!factory.getService(SessionService.class).exists(sessionId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SessionService.Session session = factory.getService(SessionService.class).getSession(sessionId);
        factory.get(DonareService.class).add(spital, date, pentru, status, session.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Donare>> getAll(){
        Iterable<Donare> donares = factory.get(DonareService.class).getAll();
        return new ResponseEntity<>(donares, HttpStatus.OK);
    }

    @RequestMapping(value = "/{sessionId}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Donare>> getByUser(@PathVariable Long sessionId) {
        if (!factory.getService(SessionService.class).exists(sessionId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SessionService.Session session = factory.getService(SessionService.class).getSession(sessionId);
        return new ResponseEntity<>(factory.get(DonareService.class).getByUser(session.getEmail()), HttpStatus.OK);

    }
}
