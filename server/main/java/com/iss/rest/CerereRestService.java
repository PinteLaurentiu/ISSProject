package com.iss.rest;

import com.iss.domain.Cerere;
import com.iss.service.BaseServiceFactory;
import com.iss.service.CerereService;
import com.iss.service.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if (strings.length != 5 && strings.length != 6){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        factory.get(CerereService.class).add((Object[]) strings);
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
}
