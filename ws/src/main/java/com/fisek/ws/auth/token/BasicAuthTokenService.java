package com.fisek.ws.auth.token;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fisek.ws.auth.dto.Credentials;
import com.fisek.ws.user.Kullanici;
import com.fisek.ws.user.KullaniciService;

@Service
public class BasicAuthTokenService implements TokenService {
    
@Autowired
KullaniciService kullaniciService ;

@Autowired
PasswordEncoder passwordEncoder;

    @Override  // Kullanıcı kimlik bilgilerini (e-posta ve şifre) birleştirir ve Base64 ile şifreler.
    public Token createToken(Kullanici user, Credentials creds) { 
        String emailColonPassword=creds.email() + ":" + creds.sifre();
        String token =Base64.getEncoder().encodeToString(emailColonPassword.getBytes());
        return new Token("Basic", token); // "Basic" şeması ile birlikte Base64 şifrelenmiş token'ı döner.
  
    }

//Bu yöntem, authorizationHeader mevcut değilse null döner.
// Header'ı Base64 ile çözerek e-posta ve şifreyi çıkarır.
// E-posta ile kullanıcıyı veritabanında arar ve şifreyi doğrular. 
//Şifre geçerliyse kullanıcıyı döner, aksi halde null döner.
    @Override
    public Kullanici verifyToken(String authorizationHeader) {
      if(authorizationHeader == null) return null;
      var base64Encoded=authorizationHeader.split("Basic ")[1];
      var decoded=new String (Base64.getDecoder().decode(base64Encoded));
      var credentials=decoded.split(":");
      var email=credentials[0];
      var sifre=credentials[1];
      Kullanici inDB=kullaniciService.findByEmail(email);
      if(inDB==null) return null;
     if(!passwordEncoder.matches(sifre, inDB.getSifre()))  return null;
     return inDB;
    }
    
}
