package com.fisek.ws.auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fisek.ws.auth.dto.AuthResponse;
import com.fisek.ws.auth.dto.Credentials;
import com.fisek.ws.auth.exception.AuthenticationException;
import com.fisek.ws.error.ApiError;

import jakarta.validation.Valid;
@RestController
public class AuthController {
    @Autowired  // AuthService'in otomatik olarak enjekte edilmesini sağlar.
    AuthService authService;
    
    @PostMapping("/api/v1/auth")
  AuthResponse handleAuthentication(@Valid @RequestBody Credentials creds){
 return authService.authenticate(creds);
    }
    // AuthenticationException türündeki istisnaları işlemek için bir işleyici tanımlar.
    @ExceptionHandler(AuthenticationException .class) 
   ResponseEntity<?> handleAuthenticationException(AuthenticationException exception) { 
      ApiError error = new ApiError(); 
      error.setPath("/api/v1/auth");
     error.setStatus(401); 
      error.setMessage(exception.getMessage()); // Hata mesajını ayarlar.
      return ResponseEntity.status(401).body(error); // HTTP 401 yanıtı ile ApiError nesnesini döner.
  }
}
