package it.internetidee.AbbreCatSin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api")
public interface CatastoSinteticoController {

    @PostMapping(value ="/catastoSintetico", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")  
    ResponseEntity<Object> richiesta(@RequestBody String token) throws Exception;
}