package it.internetidee.AbbreCatSin.dtoCatasto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "apsRisposta")
public class CatastoResponse {

    @JacksonXmlProperty(localName = "aspEsito")
    private Esito esito;
    @JacksonXmlProperty(localName = "aspDati")
    private Dati dati;

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
        @JacksonXmlProperty(localName = "codifisc")
        private String codiceFisc;
    }

    @Data
    public static class Richiesta {

        @JacksonXmlProperty(localName = "numMovEC")
        private String numMovEC;
        @JacksonXmlProperty(localName = "dataOra")
        private Data dataOra;
        @JacksonXmlProperty(localName = "url")
        private String url;
        @JacksonXmlProperty(localName = "ist")
        private String ist;
        @JacksonXmlProperty(localName = "usr")
        private String usr;
        @JacksonXmlProperty(localName = "prd")
        private String prd;
        @JacksonXmlProperty(localName = "tiposogg")
        private String tipoSogg;
        @JacksonXmlProperty(localName = "codifisc")
        private String codiceFisc;
    }
}