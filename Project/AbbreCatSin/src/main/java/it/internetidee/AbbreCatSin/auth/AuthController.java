package it.internetidee.AbbreCatSin.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.internetidee.AbbreCatSin.auth.auth_entity.AuthRequest;
import it.internetidee.AbbreCatSin.config.JwtService;
import it.internetidee.AbbreCatSin.entity.Anagrafica;
import it.internetidee.AbbreCatSin.error.CredenzialException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/api/auth")   
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService service;
    

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthRequest request ) throws Exception {
        try{
            return new ResponseEntity<Object>(service.authentication(request), HttpStatus.ACCEPTED);
        } catch (CredenzialException e){
            return new ResponseEntity<Object>(e.getClass(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/loginCrypt")             //chiamata con BCrypt
    public ResponseEntity<Object> loginCrypt(@RequestBody AuthRequest request ) throws Exception {
        try{
            return new ResponseEntity<Object>(service.authenticationCryp(request), HttpStatus.ACCEPTED);
        } catch (CredenzialException e){
            return new ResponseEntity<Object>(e.getClass(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestBody AuthRequest request) throws Exception {
        try{
            return new ResponseEntity<Object>(service.signout(request), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getClass(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUsername/{username}")
    public ResponseEntity<Object> getUser(@PathVariable String username, @RequestHeader("Authorization") String token) throws Exception {
        try{
            return new ResponseEntity<Object>(service.getUsername(username, token), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getClass(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAnagrafica/")
    public ResponseEntity<Object>getAnagra(@RequestHeader("Authorization") String token) throws Exception{
      try{ 
        return new ResponseEntity<Object>(service.getAnagrafica(token), HttpStatus.ACCEPTED);
      } catch (Exception e) {
        return new ResponseEntity<Object>(e.getClass(), HttpStatus.BAD_REQUEST);
      }
    }
    
}