package com.iss.rest;

import com.iss.service.BaseServiceFactory;
import com.iss.service.ServiceFactory;
import com.iss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/users")
public class UserRestService {

    private final BaseServiceFactory factory;
    private final AtomicLong guid = new AtomicLong(Long.MIN_VALUE);
    private final ConcurrentHashMap<Long, Date> sessions = new ConcurrentHashMap<>();

    @Autowired
    public UserRestService(ServiceFactory factory) {
        this.factory = factory;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Long> login(@RequestBody String[] strings){
        if (strings.length != 2){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String user = strings[0];
        String pass = strings[1];
        if (factory.get(UserService.class).login(user, pass)) {
            long guidValue = guid.incrementAndGet();
            sessions.put(guidValue, new Date());
            return new ResponseEntity<>(guidValue, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody String[] strings){
        if (strings.length != 12){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        factory.get(UserService.class).add((Object[]) strings);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity activation(@RequestParam("id") int id, @RequestParam("uid") String uid){
        factory.get(UserService.class).activation(id, uid);
        return new ResponseEntity(HttpStatus.OK);
    }
}
