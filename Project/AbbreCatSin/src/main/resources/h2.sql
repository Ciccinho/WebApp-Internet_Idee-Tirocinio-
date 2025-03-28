DROP TABLE IF EXISTS dati_accesso;
DROP TABLE IF EXISTS anagrafica;
CREATE TABLE anagrafica (
    id_anagrafica BIGINT PRIMARY KEY AUTO_INCREMENT,
    note VARCHAR(255),
    nominativo VARCHAR(255),
    persona_fisica BOOLEAN DEFAULT TRUE,
    codice_fiscale VARCHAR(255),
    partita_iva VARCHAR(255),
    data_nascita DATE,
    luogo_nascita VARCHAR(255),
    sesso VARCHAR(50),
    comune_nascita VARCHAR(255),
    nome VARCHAR(255),
    cognome VARCHAR(255),
    tipo_identificazione VARCHAR(255),
    data_identificazione DATE,
    natura_giuridica VARCHAR(255),
    codice_cr VARCHAR(255),
    in_contratto BOOLEAN DEFAULT FALSE,
    persona_politicamente_esposta BOOLEAN,
    nazionalita VARCHAR(255),
    deceduto BOOLEAN DEFAULT FALSE,
    data_decesso DATE,
    occupazione BOOLEAN,
    tipologia_contratto_pensione VARCHAR(255),
    data_scadenza_contratto DATE,
    data_anzianita_servizio DATE,
    data_decorrenza_pensione DATE,
    data_riferimento_info_commerciale DATE,
    emolumenti_mensili DOUBLE,
    abi_pensionati VARCHAR(255)
);
CREATE TABLE dati_accesso (
    id_dati_accesso BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    attivo BOOLEAN DEFAULT TRUE,
    last_login TIMESTAMP,
    last_logout TIMESTAMP,
    ip VARCHAR(255),
    fk_id_anagrafica BIGINT,
    fk_id_gruppo BIGINT DEFAULT 1,
    loggato BOOLEAN DEFAULT FALSE,
    ultimochecklogin TIMESTAMP,
    ultimocambiopwd TIMESTAMP,
    bloccato BOOLEAN,
    multi_accesso BOOLEAN DEFAULT FALSE,
    ndg VARCHAR(255),
    fk_id_livello_gerarchia BIGINT,
    super_user BOOLEAN,
    open_access BOOLEAN DEFAULT FALSE,
    ldap BOOLEAN DEFAULT FALSE,
    codice_2fa VARCHAR(255),
    username_voip VARCHAR(255),
    password_voip VARCHAR(255),
    sessione_scaduta BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_anagrafica FOREIGN KEY (fk_id_anagrafica) REFERENCES anagrafica(id_anagrafica)
);
-- Inserimento dati di esempio per la tabella anagrafica
INSERT INTO anagrafica (nominativo, persona_fisica, codice_fiscale, partita_iva, data_nascita, luogo_nascita, sesso, comune_nascita, nome, cognome)
VALUES ('Mario Rossi', TRUE, 'RSSMRA80A01H501U', '12345678901', '1980-01-01', 'Roma', 'M', 'Roma', 'Mario', 'Rossi');
INSERT INTO anagrafica (nominativo, persona_fisica, codice_fiscale, partita_iva, data_nascita, luogo_nascita, sesso, comune_nascita, nome, cognome)
VALUES ('Lucia Bianchi', TRUE, 'BNCLCU85B41F205X', '98765432109', '1985-04-15', 'Milano', 'F', 'Milano', 'Lucia', 'Bianchi');
-- Inserimento dati di esempio per la tabella dati_accesso
INSERT INTO dati_accesso (username, password, attivo, fk_id_anagrafica, ip)
VALUES ('mario.rossi', 'password123', TRUE, 1, '192.168.1.1');
INSERT INTO dati_accesso (username, password, attivo, fk_id_anagrafica, ip)
VALUES ('lucia.bianchi', 'securepwd', TRUE, 2, '192.168.1.2');






