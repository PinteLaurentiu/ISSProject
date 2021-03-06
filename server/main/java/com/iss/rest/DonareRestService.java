package com.iss.rest;

import com.iss.domain.Donare;
import com.iss.enums.DonareStatus;
import com.iss.enums.GrupaSange;
import com.iss.enums.Role;
import com.iss.domain.Spital;
import com.iss.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
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

    @RequestMapping(value = "/finalizare", method = RequestMethod.POST)
    public ResponseEntity<String> finalizareDonare(@RequestBody String[] strings){

        if (strings.length != 2){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Long sessionId = Long.valueOf(strings[0]);
        Integer idDonare = Integer.valueOf(strings[1]);
        if (!factory.getService(SessionService.class).exists(sessionId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SessionService.Session session = factory.getService(SessionService.class).getSession(sessionId);
        List<Role> roles = (List<Role>)factory.get(UserService.class).getRoles(session.getEmail());
        if (!roles.contains(Role.DoctorDonare)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!factory.get(DonareService.class).exists(idDonare)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        factory.get(DonareService.class).finalizare(idDonare);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public ResponseEntity<String> addTransfer(@RequestBody String[] strings) {
        if (strings.length != 3){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Long sessionId = Long.valueOf(strings[0]);
        Integer idPunga = Integer.valueOf(strings[1]);
        String to = strings[2];
        if (!factory.getService(SessionService.class).exists(sessionId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SessionService.Session session = factory.getService(SessionService.class).getSession(sessionId);
        List<Role> roles = (List<Role>)factory.get(UserService.class).getRoles(session.getEmail());
        if (!roles.contains(Role.DoctorLab)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!factory.get(DonareService.class).existsPunga(idPunga)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        factory.get(DonareService.class).addTransfer(idPunga, to);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/analiza", method = RequestMethod.POST)
    public ResponseEntity<String> addAnaliza(@RequestBody String[] strings) {
        if (strings.length != 5){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Long sessionId = Long.valueOf(strings[0]);
        Integer idDonare = Integer.valueOf(strings[1]);
        String boli = strings[2];
        String imunoH = strings[3];
        GrupaSange grupaSange = GrupaSange.fromString(strings[4]);

        if (!factory.getService(SessionService.class).exists(sessionId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SessionService.Session session = factory.getService(SessionService.class).getSession(sessionId);
        List<Role> roles = (List<Role>)factory.get(UserService.class).getRoles(session.getEmail());
        if (!roles.contains(Role.DoctorLab)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!factory.get(DonareService.class).exists(idDonare)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        factory.get(DonareService.class).addAnaliza(idDonare, boli, imunoH, grupaSange);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value = "/componente", method = RequestMethod.POST)
    public ResponseEntity<String> addComponente(@RequestBody String[] strings){
        if (strings.length != 2){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Integer idDonare = Integer.valueOf(strings[0]);
        Long sessionId = Long.valueOf(strings[1]);

        if (!factory.getService(SessionService.class).exists(sessionId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        SessionService.Session session = factory.getService(SessionService.class).getSession(sessionId);
        List<Role> roles = (List<Role>)factory.get(UserService.class).getRoles(session.getEmail());
        if (!roles.contains(Role.DoctorLab)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!factory.get(DonareService.class).exists(idDonare)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        factory.get(DonareService.class).addComponente(idDonare);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
