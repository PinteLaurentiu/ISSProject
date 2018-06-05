package com.iss.rest;

import com.iss.domain.Cerere;
import com.iss.enums.TipComponenteSange;
import com.iss.enums.GradDeUrgenta;
import com.iss.enums.GrupaSange;
import com.iss.enums.Role;
import com.iss.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/cerere")
public class CerereRestService {
    private final BaseServiceFactory factory;

    @Autowired
    public CerereRestService(ServiceFactory factory) {
        this.factory = factory;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody String[] strings){
        if (strings.length != 8){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        String nume = strings[0];
        String prenume = strings[1];
        TipComponenteSange tipComponenteSange = TipComponenteSange.valueOf(strings[2]);
        GrupaSange grupaSange = GrupaSange.fromString(strings[3]);
        GradDeUrgenta gradDeUrgenta = GradDeUrgenta.valueOf(strings[4]);
        Integer cantitatea = Integer.valueOf(strings[5]);
        String locatie = strings[6];
        Long sessionId = Long.valueOf(strings[7]);

        if (!factory.getService(SessionService.class).exists(sessionId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SessionService.Session session = factory.getService(SessionService.class).getSession(sessionId);
        List<Role> roles = (List<Role>)factory.get(UserService.class).getRoles(session.getEmail());
        if (!roles.contains(Role.DoctorSpital)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        factory.get(CerereService.class).add(nume, prenume, tipComponenteSange, grupaSange, gradDeUrgenta,
                                            cantitatea, locatie);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getPaginated", method = RequestMethod.POST)
    public ResponseEntity<Iterable<Cerere>> getAllPaginated(@RequestBody Long[] values){
        if (values.length != 3){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(factory.get(CerereService.class).getAll(values[0].intValue(), values[1].intValue()),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Integer> count() {
        return new ResponseEntity<>(factory.get(CerereService.class).count(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idCerere}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUsers(@PathVariable Integer idCerere){
        factory.get(CerereService.class).remove(idCerere);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/reemit", method = RequestMethod.POST)
    public ResponseEntity<String> reemit(@RequestBody String[] strings) {
        if (strings.length != 3)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Long sessionId = Long.valueOf(strings[2]);

        if (!factory.getService(SessionService.class).exists(sessionId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        SessionService.Session session = factory.getService(SessionService.class).getSession(sessionId);
        List<Role> roles = (List<Role>)factory.get(UserService.class).getRoles(session.getEmail());
        if (!roles.contains(Role.DoctorSpital)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String[] idsString = strings[1].split("\\|");
        Integer idCerere = Integer.valueOf(strings[0]);
        Cerere cerere = factory.get(CerereService.class).getById(idCerere);
        if (cerere == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (idsString.length > cerere.getProbe().size()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Integer> ids = new ArrayList<>();
        for (String idString : idsString) {
            ids.add(Integer.valueOf(idString));
        }
        factory.get(CerereService.class).reemit(cerere, ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
