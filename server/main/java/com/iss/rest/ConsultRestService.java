package com.iss.rest;


import com.iss.domain.*;
import com.iss.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/consulturi")
public class ConsultRestService {

    private final ServiceFactory factory;

    @Autowired
    public ConsultRestService(ServiceFactory factory) {
        this.factory = factory;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Consult>> getAll(){
        Iterable<Consult> consults = factory.get(ConsultService.class).getAll();
        return new ResponseEntity<>(consults, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody String[] strings){
        if (strings.length != 8){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Integer idConsult = Integer.valueOf(strings[0]);
        Float greutate = Float.valueOf(strings[1]);
        Float tensiune = Float.valueOf(strings[2]);
        Integer puls = Float.valueOf(strings[3]).intValue();
        String boliDepistate = strings[4];
        Float inaltime = Float.valueOf(strings[5]);
        Boolean apt = Boolean.valueOf(strings[6]);
        Long sessionId = Long.valueOf(strings[7]);

        if (!factory.getService(SessionService.class).exists(sessionId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SessionService.Session session = factory.getService(SessionService.class).getSession(sessionId);
        List<Role> roles = (List<Role>)factory.get(UserService.class).getRoles(session.getEmail());
        if (!roles.contains(Role.DoctorDonare)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        factory.get(ConsultService.class).add(idConsult, greutate, tensiune, puls,
                                                boliDepistate,inaltime,apt);
        return new ResponseEntity(HttpStatus.OK);
    }
}
