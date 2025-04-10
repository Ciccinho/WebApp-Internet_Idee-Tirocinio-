package it.internetidee.AbbreCatSin.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import it.internetidee.AbbreCatSin.controller.CatastoSinteticoController;
import it.internetidee.AbbreCatSin.dtoCatasto.CatastoRequest;
import it.internetidee.AbbreCatSin.dtoCatasto.CatastoResponse;
import it.internetidee.AbbreCatSin.service.CatastoSinteticoService;


@RestController
@RequiredArgsConstructor
public class CatastoSinteticoControllerImpl implements CatastoSinteticoController {

    private final CatastoSinteticoService service;


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