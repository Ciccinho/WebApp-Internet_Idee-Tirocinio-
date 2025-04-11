package it.internetidee.AbbreCatSin.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import it.internetidee.AbbreCatSin.dtoCatasto.CatastoRequest;
import it.internetidee.AbbreCatSin.dtoCatasto.CatastoResponse;

import java.io.ByteArrayOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

    public byte[] generaExRepo(CatastoResponse response) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Catasto Report "+ response.getDati().getAPar().getCodiceFisc());
        int numRow = 0;
        Row headeRow = sheet.createRow(numRow++);
        headeRow.createCell(0).setCellValue("Codice");
        headeRow.createCell(1).setCellValue("Descrizione");
        if( response.getEsito() != null ){
            Row dataRow = sheet.createRow(numRow++);
            dataRow.createCell(0).setCellValue(response.getEsito().getCodice());
            dataRow.createCell(1).setCellValue(response.getEsito().getDescrizione());
        }
        headeRow = sheet.createRow(numRow++);
        headeRow.createCell(0).setCellValue("Id Report");
        if( response.getDati() != null ){
            Row dataRow = sheet.createRow(numRow++);
            dataRow.createCell(0).setCellValue(response.getDati().getIdReport());
        }
        headeRow = sheet.createRow(numRow++);
        headeRow.createCell(0).setCellValue("Tipo Soggetto");
        headeRow.createCell(1).setCellValue("Codice Fiscale");
        if( response.getDati().getAPar() != null ){
            Row dataRow = sheet.createRow(numRow++);
            dataRow.createCell(0).setCellValue(response.getDati().getAPar().getTipoSogg());
            dataRow.createCell(1).setCellValue(response.getDati().getAPar().getCodiceFisc());
        }
        headeRow = sheet.createRow(numRow++);
        headeRow.createCell(0).setCellValue("Numero Movimento EC");
        headeRow.createCell(1).setCellValue("Data Ora");
        headeRow.createCell(2).setCellValue("URL");
        headeRow.createCell(3).setCellValue("Denominazione Istanza");
        headeRow.createCell(4).setCellValue("Utente");
        headeRow.createCell(5).setCellValue("Prodotto");
        headeRow.createCell(6).setCellValue("Tipo Soggetto");
        headeRow.createCell(7).setCellValue("Codice Fiscale");
        if(response.getRichiesta() != null){
            Row dataRow = sheet.createRow(numRow++);
            dataRow.createCell(0).setCellValue(response.getRichiesta().getNumMovEC());
            String dataOra = response.getRichiesta().getDataOra().toString();
            dataRow.createCell(1).setCellValue(dataOra);
            dataRow.createCell(2).setCellValue(response.getRichiesta().getUrl());
            dataRow.createCell(3).setCellValue(response.getRichiesta().getIst());
            dataRow.createCell(4).setCellValue(response.getRichiesta().getUsr());
            dataRow.createCell(5).setCellValue(response.getRichiesta().getPrd());
            dataRow.createCell(6).setCellValue(response.getRichiesta().getTipoSogg());
            dataRow.createCell(7).setCellValue(response.getRichiesta().getCodiceFisc());
        }
        ByteArrayOutputStream xcelReport = new ByteArrayOutputStream();
        workbook.write(xcelReport);
        workbook.close();
        return xcelReport.toByteArray();
    }
}