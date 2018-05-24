package com.iss.rest;

import com.iss.domain.Role;
import com.iss.domain.User;
import com.iss.service.BaseServiceFactory;
import com.iss.service.ServiceFactory;
import com.iss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/users")
public class UserRestService {
    private class Session {
        private Date expire;
        private String email;

        Session(Date expire, String email) {
            this.expire = expire;
            this.email = email;
        }
    }
    private final BaseServiceFactory factory;
    private final AtomicLong guid = new AtomicLong(Long.MIN_VALUE);
    private final ConcurrentHashMap<Long, Session> sessions = new ConcurrentHashMap<>();

    @Autowired
    public UserRestService(ServiceFactory factory) {
        this.factory = factory;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Long> login(@RequestBody String[] strings){
        if (strings.length != 2){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String email = strings[0];
        String pass = strings[1];
        if (factory.get(UserService.class).login(email, pass)) {
            long guidValue = guid.incrementAndGet();

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MINUTE, 30);

            Session session = new Session(cal.getTime(), email);
            sessions.put(guidValue, session);
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

    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public ResponseEntity<Iterable<Role>> getRoles(@RequestBody Long sessionId){
        if (!sessions.containsKey(sessionId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Session session = sessions.get(sessionId);
        return new ResponseEntity<>(factory.get(UserService.class).getRoles(session.email), HttpStatus.OK);
    }

    @RequestMapping(value = "/getPaginated", method = RequestMethod.POST)
    public ResponseEntity<Iterable<User>> getAllPaginated(@RequestBody Long[] values){
        if (values.length != 3){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Role> roles = (List<Role>)factory.get(UserService.class).getRoles(sessions.get(values[2]).email);
        if (!roles.contains(Role.UsersEditor)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(factory.get(UserService.class).getAll(values[0].intValue(), values[1].intValue()),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Integer> count() {
        return new ResponseEntity<>(factory.get(UserService.class).count(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{sessionId}",method = RequestMethod.PUT)
    public ResponseEntity editRoles(@RequestBody User user, @PathVariable Long sessionId) {

        //noinspection SuspiciousMethodCalls
        Session session = sessions.get(sessionId);
        List<Role> roles = (List<Role>)factory.get(UserService.class).getRoles(session.email);
        if (!roles.contains(Role.UsersEditor)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        factory.get(UserService.class).edit(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{sessionId}/{key}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUsers(@PathVariable Integer key, @PathVariable Long sessionId){
        Session session = sessions.get(sessionId);
        List<Role> roles = (List<Role>)factory.get(UserService.class).getRoles(session.email);
        if (!roles.contains(Role.UsersEditor)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        factory.get(UserService.class).remove(key);
        return new ResponseEntity(HttpStatus.OK);
    }
}
