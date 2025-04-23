package it.internetidee.AbbreCatSin.dtoCatasto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Data;

@Data 
@JacksonXmlRootElement(localName = "apsRisposta")
public class CatastoResponse {

    @JacksonXmlProperty(localName = "apsEsito")
    private Esito Esito;
    @JacksonXmlProperty(localName = "apsDati")
    private Dati dati;
    @JacksonXmlProperty(localName =  "apsRichiesta")
    private Richiesta richiesta;

    @Data
    public static class Esito {
        
        @JacksonXmlProperty(localName = "codice")
        private int codice;
        @JacksonXmlProperty(localName = "descrizione")
        private String descrizione;
    }

    @Data
    public static class Dati {
    
        @JacksonXmlProperty(localName = "idReport")
        private String idReport;
        @JacksonXmlProperty(localName = "aPar")
        private Parametri aPar;
    }

    @Data

    public static class Parametri {
    
        @JacksonXmlProperty(localName = "tiposogg")
        private String tipoSogg;
        @JacksonXmlProperty(localName = "codiFisc")
        private String codiceFisc;
    };

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Richiesta {

        @JacksonXmlProperty(localName = "numMovEC")
        private String numMovEC;
        @JacksonXmlProperty(localName =  "dataOra")
        private String dataOra;
        @JacksonXmlProperty(localName =  "url")
        private String url;
        @JacksonXmlProperty(localName =  "ist")
        private String ist;
        @JacksonXmlProperty(localName =  "usr")
        private String usr;
        @JacksonXmlProperty(localName =  "prodotto")
        private String prd;
        @JacksonXmlProperty(localName =  "tiposogg")
        private String tipoSogg;
        @JacksonXmlProperty(localName =  "codifisc")
        private String codiceFisc;
        @JacksonXmlProperty(localName =  "tipoxml")
        private String tipoxml;
    }
}