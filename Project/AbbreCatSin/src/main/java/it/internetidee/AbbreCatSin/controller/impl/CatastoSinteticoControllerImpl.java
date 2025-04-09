package it.internetidee.AbbreCatSin.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import it.internetidee.AbbreCatSin.controller.CatastoSinteticoController;
import it.internetidee.AbbreCatSin.dtoCatasto.CatastoRequest;
import it.internetidee.AbbreCatSin.dtoCatasto.CatastoResponse;
import it.internetidee.AbbreCatSin.service.CatastoSinteticoService;

@RestController
public class CatastoSinteticoControllerImpl implements CatastoSinteticoController {

    @Autowired
    private final CatastoSinteticoService service;

    public CatastoSinteticoControllerImpl (CatastoSinteticoService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<CatastoResponse> richiesta(CatastoRequest request) {
        try{
            CatastoResponse response = service.richiediReport(request);
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}