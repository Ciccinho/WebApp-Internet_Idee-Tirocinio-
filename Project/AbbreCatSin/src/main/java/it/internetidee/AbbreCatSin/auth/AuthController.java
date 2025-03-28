package it.internetidee.AbbreCatSin.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.internetidee.AbbreCatSin.auth.auth_entity.AuthRequest;
import it.internetidee.AbbreCatSin.error.CredenzialException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")   
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
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

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestBody AuthRequest request) throws Exception {
        try{
            return new ResponseEntity<Object>(service.signout(request), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getClass(), HttpStatus.BAD_REQUEST);
        }
    }
    
}