package com.iss.rest;

import com.iss.domain.Spital;
import com.iss.service.BaseServiceFactory;
import com.iss.service.ServiceFactory;
import com.iss.service.SpitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/spitale")
@RestController
public class SpitalRestService {

    private final BaseServiceFactory factory;
    
    @Autowired
    public SpitalRestService(ServiceFactory factory) {
        this.factory = factory;
    }
    
    
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Spital>> getAll(){
        return new ResponseEntity<>(factory.get(SpitalService.class).getAll(), HttpStatus.OK);
    }
}
