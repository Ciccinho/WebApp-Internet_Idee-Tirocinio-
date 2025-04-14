package it.internetidee.AbbreCatSin.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import it.internetidee.AbbreCatSin.dtoCatasto.CatastoRequest;

@RequestMapping("/api")
public interface CatastoSinteticoController {

    @PutMapping("/catastoSintetico")  
    ResponseEntity<Resource> richiesta(@RequestBody CatastoRequest request);
}