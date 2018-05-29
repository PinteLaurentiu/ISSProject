package com.iss.rest;

import com.iss.enums.Role;
import com.iss.domain.User;
import com.iss.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserRestService {

    private final BaseServiceFactory factory;

    @Autowired
    public UserRestService(ServiceFactory factory) {
        this.factory = factory;
        //TODO remove sesions job
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Long> login(@RequestBody String[] strings){
        if (strings.length != 2){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String email = strings[0];
        String pass = strings[1];
        if (factory.get(UserService.class).login(email, pass)) {
            if (!factory.get(UserService.class).isActivated(email))
                return new ResponseEntity<>(Long.MAX_VALUE, HttpStatus.OK);
            return new ResponseEntity<>(factory.getService(SessionService.class).createSession(email), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody String[] strings){
        if (strings.length != 12 && strings.length != 13){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (strings.length == 13) {
            SessionService.Session session = factory.getService(SessionService.class).getSession(Long.valueOf(strings[12]));
            List<Role> roles = (List<Role>)factory.get(UserService.class).getRoles(session.getEmail());
            if (!roles.contains(Role.UsersEditor)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
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
        if (!factory.getService(SessionService.class).exists(sessionId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        SessionService.Session session = factory.getService(SessionService.class).getSession(sessionId);
        return new ResponseEntity<>(factory.get(UserService.class).getRoles(session.getEmail()), HttpStatus.OK);
    }

    @RequestMapping(value = "/getPaginated", method = RequestMethod.POST)
    public ResponseEntity<Iterable<User>> getAllPaginated(@RequestBody Long[] values){
        if (values.length != 3){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SessionService.Session session = factory.getService(SessionService.class).getSession(values[2]);
        List<Role> roles = (List<Role>)factory.get(UserService.class).getRoles(session.getEmail());
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
        SessionService.Session session = factory.getService(SessionService.class).getSession(sessionId);
        List<Role> roles = (List<Role>)factory.get(UserService.class).getRoles(session.getEmail());
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
        SessionService.Session session = factory.getService(SessionService.class).getSession(sessionId);
        List<Role> roles = (List<Role>)factory.get(UserService.class).getRoles(session.getEmail());
        if (!roles.contains(Role.UsersEditor)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        factory.get(UserService.class).remove(key);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/resendEmail", method = RequestMethod.POST)
    public ResponseEntity<String> resendEmail(@RequestBody String email) {
        User user = factory.get(UserService.class).getByEmail(email);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        factory.getService(MailService.class).sendActivationMail(email, user.getId(), user.getActivation().getGeneratedId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getByEmail/{email}", method = RequestMethod.GET)
    public ResponseEntity<User> count(@PathVariable String email) {
        return new ResponseEntity<>(factory.get(UserService.class).getByEmail(email), HttpStatus.OK);
    }
}
