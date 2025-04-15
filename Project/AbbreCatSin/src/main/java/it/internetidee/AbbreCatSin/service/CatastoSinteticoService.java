package it.internetidee.AbbreCatSin.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import it.internetidee.AbbreCatSin.config.JwtService;
import it.internetidee.AbbreCatSin.dtoCatasto.CatastoResponse;
import it.internetidee.AbbreCatSin.entity.Anagrafica;
import lombok.var;
import it.internetidee.AbbreCatSin.dao.AnagraficaDao;
import it.internetidee.AbbreCatSin.dao.UserDao;

import java.io.ByteArrayOutputStream;

import javax.naming.NameNotFoundException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class CatastoSinteticoService {

    @Value("${catasto.service.base-url}")
    private String baseUrl;
    @Value("${catasto.service.abbrevia.ist}") 
    private String ist;
    @Value("${catasto.service.urs}")
    private String usr;
    @Value("${catasto.service.pwd}")
    private String pwd;
    @Value("${catasto.service.prd}")
    private String prodotto;
    @Value("${catasto.service.mock}")
    private boolean mock;
    
    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;
    private final JwtService jwtService;
    private final UserDao userDao;
    private final AnagraficaDao anagraficaDao;


    public CatastoSinteticoService() {
        this.restTemplate = new RestTemplate();
        this.xmlMapper = new XmlMapper();
        this.jwtService = new JwtService();
        this.userDao = null;
        this.anagraficaDao = null;
    }

    public CatastoResponse richiediReport(String token) throws Exception {   
        if(mock){
            CatastoResponse response = new CatastoResponse();
            response.getEsito().setCodice(11);
            response.getEsito().setDescrizione("descrizione");
            response.getDati().setIdReport("id");
            response.getDati().setAPar(null);
            response.getRichiesta().setCodiceFisc("codice fiscale");
            response.getRichiesta().setDataOra(null);
            response.getRichiesta().setIst("istanza");
            response.getRichiesta().setNumMovEC("numero MOV");
            response.getRichiesta().setPrd("prodotto");
            response.getRichiesta().setTipoSogg("persona fisica");
            response.getRichiesta().setUrl("url");
            response.getRichiesta().setUsr("User");
            return response;
        }

        if(token.startsWith("Bearer "))
                token = token.substring(7);
            String username = jwtService.extractUsername(token);
            System.out.println("token:" + token );
            var user = userDao.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
            Anagrafica anagrafica = anagraficaDao.findById((user.getAnagrafica().getId())).orElseThrow(()-> new NameNotFoundException("Anagrafica not found"));
            String codFisc = anagrafica.getCodiceFiscale();
            String tipoSog = new String();
            boolean tipo = anagrafica.getPersonaFisica();
            if(tipo == true)
                tipoSog = "F";
            else
                tipoSog = "G";
        UriComponentsBuilder uriBilder = UriComponentsBuilder.fromUriString(baseUrl)            // risposta con file XML
            .queryParam("ist", ist)
            .queryParam("usr", usr)
            .queryParam("pwd", pwd)
            .queryParam("prodotto", prodotto)
            .queryParam("tipoSogg", tipoSog)
            .queryParam("codiFisc", codFisc)
            .queryParam("tipoXml", "xml");
        String responseXml = restTemplate.getForObject(uriBilder.toUriString(), String.class);
        

        return xmlMapper.readValue(responseXml, CatastoResponse.class);
        
    }

    public byte[] generaExRepo(CatastoResponse response) throws Exception {                     //creazione file excel dal file XML di risposta
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