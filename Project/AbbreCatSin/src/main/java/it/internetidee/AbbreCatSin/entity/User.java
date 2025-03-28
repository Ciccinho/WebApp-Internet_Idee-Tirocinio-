package it.internetidee.AbbreCatSin.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dati_accesso")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dati_accesso")
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column
    private Boolean attivo = true;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    @Column(name = "last_logout")
    private LocalDateTime lastLogout;
    @Column
    private String ip;
    // Relazione con Anagrafica
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_anagrafica")
    @ToString.Exclude
    private Anagrafica anagrafica;
    @Column(name = "fk_id_gruppo")
    private Long fkIdGruppo = 1L;       // valore fisso
    @Column
    private Boolean loggato = false;
    @Column(name = "ultimochecklogin")
    private LocalDateTime ultimoCheckLogin;
    @Column(name = "ultimocambiopwd")
    private LocalDateTime ultimoCambioPwd;
    @Column
    private Boolean bloccato;
    @Column(name = "multi_accesso")
    private Boolean multiAccesso = false;
    @Column
    private String ndg;
    @Column(name = "fk_id_livello_gerarchia")
    private Long fkIdLivelloGerarchia;
    @Column(name = "super_user")
    private Boolean superUser;
    @Column(name = "open_access")
    private Boolean openAccess = false;
    @Column
    private Boolean ldap = false;
    @Column(name = "codice_2fa")
    private String codice2fa;
    @Column(name = "username_voip")
    private String usernameVoip;
    @Column(name = "password_voip")
    private String passwordVoip;
    @Column(name = "sessione_scaduta")
    private Boolean sessioneScaduta = false;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}