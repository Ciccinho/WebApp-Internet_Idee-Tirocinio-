package it.internetidee.AbbreCatSin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import it.internetidee.AbbreCatSin.dtoCatasto.CatastoRequest;
import it.internetidee.AbbreCatSin.dtoCatasto.CatastoResponse;

@RequestMapping("/api")
public interface CatastoSinteticoController {

    @GetMapping("/catastoSintetico/")   //mancanza del percorso
    ResponseEntity<CatastoResponse> richiesta(@RequestBody CatastoRequest request);
}
