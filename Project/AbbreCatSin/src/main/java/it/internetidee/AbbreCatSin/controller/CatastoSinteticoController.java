package it.internetidee.AbbreCatSin.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api")
public interface CatastoSinteticoController {

    @PutMapping("/catastoSintetico")  
    ResponseEntity<Resource> richiesta(@RequestParam String tipo, @RequestParam String cf);
}