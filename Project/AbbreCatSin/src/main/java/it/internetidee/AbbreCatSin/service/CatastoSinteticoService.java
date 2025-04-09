package it.internetidee.AbbreCatSin.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import it.internetidee.AbbreCatSin.dtoCatasto.CatastoRequest;
import it.internetidee.AbbreCatSin.dtoCatasto.CatastoResponse;

import org.springframework.beans.factory.annotation.Value;

@Service
public class CatastoSinteticoService {

    @Value("${base-url}")
    private String baseUrl;
    @Value("${abbrevia.inst}") 
    private String ist;
    @Value("${usr}")
    private String usr;
    @Value("${pwd}")
    private String pwd;
    @Value("${prodotto}")
    private String prodotto;

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;


    public CatastoSinteticoService() {
        this.restTemplate = new RestTemplate();
        this.xmlMapper = new XmlMapper();
    }

    public CatastoResponse richiediReport(CatastoRequest request) throws Exception {
        UriComponentsBuilder uriBilder = UriComponentsBuilder.fromUriString(baseUrl)
            .queryParam("ist", ist)
            .queryParam("usr", usr)
            .queryParam("pwd", pwd)
            .queryParam("prodotto", prodotto)
            .queryParam("tipoSogg", request.getTipoSogg())
            .queryParam("codiFisc", request.getCodFisc())
            .queryParam("tipoXml", "xml");
        String responseXml = restTemplate.getForObject(uriBilder.toUriString(), String.class);
        return xmlMapper.readValue(responseXml, CatastoResponse.class);
    }

}