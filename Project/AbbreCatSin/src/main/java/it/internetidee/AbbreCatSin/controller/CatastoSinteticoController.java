package it.internetidee.AbbreCatSin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import it.internetidee.AbbreCatSin.dtoCatasto.CatastoRequest;
import it.internetidee.AbbreCatSin.dtoCatasto.CatastoResponse;

public interface CatastoSinteticoController {

    @PostMapping("")   //mancanza del percorso
    ResponseEntity<CatastoResponse> richiesta(@RequestBody CatastoRequest request);
}
