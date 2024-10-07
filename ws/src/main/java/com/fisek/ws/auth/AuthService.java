package com.fisek.ws.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fisek.ws.auth.dto.AuthResponse;
import com.fisek.ws.auth.dto.Credentials;
import com.fisek.ws.auth.exception.AuthenticationException;
import com.fisek.ws.auth.token.Token;
import com.fisek.ws.auth.token.TokenService;
import com.fisek.ws.user.Kullanici;
import com.fisek.ws.user.KullaniciService;
import com.fisek.ws.user.dto.UserDTO;

@Service
public class AuthService {

    @Autowired
    KullaniciService kullaniciService; // Şifreleri şifrelemek ve doğrulamak için kullanılan encoder nesnesi.

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;

     public AuthResponse authenticate(Credentials creds) {
    Kullanici inDB=kullaniciService.findByEmail(creds.email());
    if(inDB== null) throw new AuthenticationException();// Kullanıcı bulunamazsa, kimlik doğrulama istisnası fırlatır.
    if(!passwordEncoder.matches(creds.sifre(), inDB.getSifre()))  throw new AuthenticationException();
    Token token =tokenService.createToken(inDB, creds);
      // Kimlik doğrulama yanıtını oluşturur ve yanıt nesnesine token ve kullanıcı bilgilerini ekler.
    AuthResponse authResponse=new AuthResponse();
    authResponse.setToken(token);
    authResponse.setUser(new UserDTO(inDB));
    return authResponse;

    }
    
}
