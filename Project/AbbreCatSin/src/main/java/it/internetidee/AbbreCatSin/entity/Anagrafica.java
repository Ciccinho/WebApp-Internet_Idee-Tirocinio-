package it.internetidee.AbbreCatSin.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "anagrafica")
public class Anagrafica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anagrafica")
    private Long id;
    @Column
    private String note;
    @Column
    private String nominativo;
    @Column(name = "persona_fisica")
    private Boolean personaFisica = true;
    @Column(name = "codice_fiscale")
    private String codiceFiscale;
    @Column(name = "partita_iva")
    private String partitaIva;
    @Column(name = "data_nascita")
    private LocalDate dataNascita;
    @Column(name = "luogo_nascita")
    private String luogoNascita;
    @Column
    private String sesso;
    @Column(name = "comune_nascita")
    private String comuneNascita;
    @Column
    private String nome;
    @Column
    private String cognome;
    @Column(name = "tipo_identificazione")
    private String tipoIdentificazione;
    @Column(name = "data_identificazione")
    private LocalDate dataIdentificazione;
    @Column(name = "natura_giuridica")
    private String naturaGiuridica;
    @Column(name = "codice_cr")
    private String codiceCr;
    @Column(name = "in_contratto")
    private Boolean inContratto = false;
    @Column(name = "persona_politicamente_esposta")
    private Boolean personaPoliticamenteEsposta;
    @Column
    private String nazionalita;
    @Column
    private Boolean deceduto = false;
    @Column(name = "data_decesso")
    private LocalDate dataDecesso;
    @Column
    private Boolean occupazione;
    @Column(name = "tipologia_contratto_pensione")
    private String tipologiaContrattoPensione;
    @Column(name = "data_scadenza_contratto")
    private LocalDate dataScadenzaContratto;
    @Column(name = "data_anzianita_servizio")
    private LocalDate dataAnzianitaServizio;
    @Column(name = "data_decorrenza_pensione")
    private LocalDate dataDecorrenzaPensione;
    @Column(name = "data_riferimento_info_commerciale")
    private LocalDate dataRiferimentoInfoCommerciale;
    @Column(name = "emolumenti_mensili")
    private Double emolumentiMensili;
    @Column(name = "abi_pensionati")
    private String abiPensionati;
}