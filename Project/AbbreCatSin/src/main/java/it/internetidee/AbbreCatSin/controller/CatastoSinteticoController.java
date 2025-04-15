package it.internetidee.AbbreCatSin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api")
public interface CatastoSinteticoController {

    @PostMapping("/catastoSintetico")  
    ResponseEntity<Object> richiesta(@RequestHeader String token) throws Exception;
}