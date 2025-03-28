package it.internetidee.AbbreCatSin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import it.internetidee.AbbreCatSin.dao.AnagraficaDao;
import it.internetidee.AbbreCatSin.dao.UserDao;
import it.internetidee.AbbreCatSin.entity.Anagrafica;
import it.internetidee.AbbreCatSin.entity.User;

@DataJpaTest
public class UserTest {

    @Autowired
    private UserDao userRepository;

    @Autowired
    private AnagraficaDao anagraficaRep;

        //TEST PERSISTENZA USER
    @Test
    public void testSaveUser() {
        User user = new User();

        user.setUsername("User1");
        user.setPassword("password");
        user.setAttivo(true);
        user.setLastLogin(LocalDateTime.now());
        user.setLastLogout(LocalDateTime.now().plusHours(1));
        user.setIp("192.168.1.2");
        user.setFkIdGruppo(1L);
        user.setLoggato(false);
        user.setUltimoCheckLogin(LocalDateTime.now().plusMinutes(10));
        user.setUltimoCambioPwd(LocalDateTime.now().minusDays(2));
        user.setBloccato(false);
        user.setMultiAccesso(true);
        user.setSuperUser(false);
        user.setOpenAccess(false);
        user.setLdap(false);
        user.setCodice2fa("987654321");
        user.setUsernameVoip("voipUser");
        user.setPasswordVoip("voipPassword");
        user.setSessioneScaduta(false);

        User saveUser = userRepository.save(user);
        assertNotNull(saveUser.getId());

    }

    public User createUser(User user) { 
        return userRepository.save(user);
    }

        //TEST CREAZIONE USER
    @Test
    public void testCreator() {
        User user = new User();
        user.setUsername("User_1");
        user.setPassword("Password_1");
        createUser(user);
        System.out.println("id: "+user.getId()+" Nome User: "+user.getUsername());
    }

        //TEST PERSISTENZA ANAGRAFICA
    @Test
    public void testSevaAnagrafica() {
        Anagrafica anagrafica = new Anagrafica();

        anagrafica.setNome("Giacomo");
        anagrafica.setCognome("Poretti");
        anagrafica.setCodiceFiscale("PRTGCM59B27C352V");
        anagrafica.setDataNascita(LocalDate.of(1959,04,27));
        anagrafica.setLuogoNascita("Palermo");
        anagrafica.setSesso("M");
        anagrafica.setNominativo("Giacomino Poretti");
        anagrafica.setNazionalita("Italiana");
        Anagrafica saveAnagrafica = anagraficaRep.save(anagrafica);

        assertNotNull(anagrafica.getId());
        assertEquals("Giacomo", saveAnagrafica.getNome());
        assertEquals("Poretti", saveAnagrafica.getCognome());
    }
    

        //TEST DI VERIFICA RICERCA ANAGRAFICA PER ID
    @Test
    public void testAnagraficaById() {
        Anagrafica anagrafica = new Anagrafica();

        anagrafica.setNome("Giovanni");
        anagrafica.setCognome("Storti");
        anagrafica.setCodiceFiscale("STRGVN59B27C352V");
        anagrafica.setDataNascita(LocalDate.of(1960, 05, 15));
        anagrafica.setLuogoNascita("Milano");
        anagrafica.setSesso("M");
        anagrafica.setNominativo("Giovanni Storti");
        anagrafica.setNazionalita("Italiana");
        Anagrafica saveAnagrafica = anagraficaRep.save(anagrafica);

        Anagrafica findAnagrafica = anagraficaRep.findById(saveAnagrafica.getId()).orElse(null);
        assertNotNull(findAnagrafica);
        assertEquals("Giovanni", findAnagrafica.getNome());
        assertEquals("Storti", findAnagrafica.getCognome());
    }


        //TEST DI VERIFICA COLLEGAMENTO TRA USER E ANAGRAFICA
    @Test
    public void testUserAnagrafica() {
        Anagrafica anagrafica = new Anagrafica();

        anagrafica.setNome("Aldo");
        anagrafica.setCognome("Baglio");
        anagrafica.setCodiceFiscale("BGLLDA60L07G273L");
        anagrafica.setDataNascita(LocalDate.of(1960, 07, 07));
        anagrafica.setLuogoNascita("Palermo");
        anagrafica.setSesso("M");
        anagrafica.setNominativo("Aldo Baglio");
        anagrafica.setNazionalita("Italiana");
        Anagrafica saveAnagrafica = anagraficaRep.save(anagrafica);

        User user = new User();
        user.setUsername("Aldo03");
        user.setPassword("passw0123");
        user.setAnagrafica(saveAnagrafica);
        User saveUser = userRepository.save(user);

        assertNotNull(saveUser.getId());
        assertNotNull(saveUser.getAnagrafica());
        assertEquals(saveAnagrafica.getId(), saveUser.getAnagrafica().getId());
    }
 
}
