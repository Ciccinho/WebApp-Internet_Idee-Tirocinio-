package it.internetidee.AbbreCatSin.dtoCatasto;

import java.time.LocalDateTime;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "apsRisposta")
@XmlAccessorType(XmlAccessType.FIELD)
public class CatastoResponse {

    @XmlElement(name = "apsEsito")
    private Esito esito;
    @XmlElement(name = "apsDati")
    private Dati dati;
    @XmlElement(name = "apsRichiesta")
    private Richiesta richiesta;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Esito {
        
        @XmlElement(name = "codice")
        private int codice;
        @XmlElement(name = "descrizione")
        private String descrizione;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Dati {
    
        @XmlElement(name = "idReport")
        private String idReport;
        @XmlElement(name = "aPar")
        private Parametri aPar;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Parametri {
    
        @XmlElement(name = "tiposogg")
        private String tipoSogg;
        @XmlElement(name = "codfisc")
        private String codiceFisc;
    };

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Richiesta {

        @XmlElement(name = "numMovEC")
        private String numMovEC;
        @XmlElement(name = "dataOra")
        private LocalDateTime dataOra;
        @XmlElement(name = "url")
        private String url;
        @XmlElement(name = "ist")
        private String ist;
        @XmlElement(name = "usr")
        private String usr;
        @XmlElement(name = "prd")
        private String prd;
        @XmlElement(name = "tiposogg")
        private String tipoSogg;
        @XmlElement(name = "codfisc")
        private String codiceFisc;
    }
}