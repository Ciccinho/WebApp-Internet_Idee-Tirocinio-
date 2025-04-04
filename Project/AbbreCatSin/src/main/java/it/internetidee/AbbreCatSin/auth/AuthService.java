package it.internetidee.AbbreCatSin.auth;

import javax.naming.NameNotFoundException;
import javax.security.auth.login.CredentialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import it.internetidee.AbbreCatSin.auth.auth_entity.AuthRequest;
import it.internetidee.AbbreCatSin.auth.auth_entity.AuthResponse;
import it.internetidee.AbbreCatSin.config.JwtService;
import it.internetidee.AbbreCatSin.dao.AnagraficaDao;
import it.internetidee.AbbreCatSin.dao.UserDao;
import it.internetidee.AbbreCatSin.entity.Anagrafica;
import it.internetidee.AbbreCatSin.entity.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final UserDao userDao;
    @Autowired 
    private final AnagraficaDao anagraficaDao;
    private final PasswordEncoder passwordEncode; 
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

                               
    public AuthResponse authentication(AuthRequest request) throws CredentialException {                 // LOGIN senza BCrypt //
        try{
            authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            var user = userDao.findByUsername(request.getUsername()).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
            var userPassCod = user.getPassword();
            if(request.getPassword().equals(userPassCod)){
                var jwtToken = jwtService.generateToken((UserDetails) user);
                return AuthResponse.builder().token(jwtToken).build();
            } else {
                throw new ExpiredJwtException(null, null, "Token expired or invalid");
            }
        } catch (BadCredentialsException e) {
            throw new CredentialException("Incorrect credenzial");
        }
    }

    public AuthResponse authenticationCryp(AuthRequest request) throws CredentialException {                 // LOGIN con BCrypt//
        try{
            authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            var user = userDao.findById(request.getId()).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
            var userPassCod = user.getPassword();
            if(passwordEncode.matches(request.getPassword(), userPassCod)){
                var jwtToken = jwtService.generateToken((UserDetails) user);
                return AuthResponse.builder().token(jwtToken).build();
            } else {
                throw new CredentialException("Token expired or invalid");
            }
        } catch (BadCredentialsException e) {
            throw new CredentialException("Incorrect credenzial");
        }
    }

    public AuthResponse signout (AuthRequest request) throws CredentialException{
        try{
            authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            var user = userDao.findById(request.getId()).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
            var jwtToken = jwtService.generateLogoutToken((UserDetails) user);
            return AuthResponse.builder().token(jwtToken).build();
        } catch (BadCredentialsException e) {
            throw new CredentialException("Error");
        }
    }

    public User getUsername(String username, String token) {
        User user =  userDao.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
        return user;
    }

    public Anagrafica getAnagrafica(String username, String token) throws Exception {
       try {
            User user = userDao.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
            Anagrafica anagrafica = anagraficaDao.findById((user.getAnagrafica().getId())).orElseThrow(()-> new NameNotFoundException("Anagrafica not found"));
            return anagrafica;
       } catch (ExpiredJwtException e){
            throw new SecurityException("token expired");
       } catch (Exception e) {
            throw new Exception("Error recovery");
       }
    }

}