package it.internetidee.AbbreCatSin.auth;

import javax.security.auth.login.CredentialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.internetidee.AbbreCatSin.auth.auth_entity.AuthRequest;
import it.internetidee.AbbreCatSin.auth.auth_entity.AuthResponse;
import it.internetidee.AbbreCatSin.config.JwtService;
import it.internetidee.AbbreCatSin.dao.UserDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final UserDao userDao; 
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
                throw new CredentialException("Token expired or invalid");
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
            //var passEncoded = encodePassword(request.getPassword());
            if(passwordEncode.matches(request.getPassword() /* passEncoded */, userPassCod)){
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
            var user = userDao.findById(request.getId()).orElseThrow(()-> new UsernameNotFoundException("Username not faound"));
            var jwtToken = jwtService.generateLogoutToken((UserDetails) user);
            return AuthResponse.builder().token(jwtToken).build();
        } catch (BadCredentialsException e) {
            throw new CredentialException("Error");
        }
    }
}