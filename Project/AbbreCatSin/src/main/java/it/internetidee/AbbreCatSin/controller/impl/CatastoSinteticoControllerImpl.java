package it.internetidee.AbbreCatSin.controller.impl;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<Resource> richiesta(@RequestBody CatastoRequest request) {
        try{
            if(request.getTipoSogg() != null && request.getCodFisc() != null) {
                CatastoResponse response = service.richiediReport(new CatastoRequest(request.getTipoSogg(), request.getCodFisc()));
                try{
                    byte[] excel = service.generaExRepo(response);
                    HttpHeaders header = new HttpHeaders();
                    header.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
                    header.setContentLength(excel.length);
                    header.set(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"catasto_report.xlsx\"");
                    ByteArrayResource resourse = new ByteArrayResource(excel);
                    return ResponseEntity.ok().headers(header).body(resourse);
                } catch (Exception e) {
                    return ResponseEntity.internalServerError().build();
                }
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch(Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}