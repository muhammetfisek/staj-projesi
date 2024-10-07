package com.fisek.ws.error;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice // Bu sınıf, tüm kontrolcüler için global hata işleme sağlar.
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler({DisabledException.class,AccessDeniedException.class}) 
   ResponseEntity<ApiError> handleDisabledException(Exception exception,HttpServletRequest request) { 
      ApiError error = new ApiError(); // Yeni bir ApiError nesnesi oluşturur.
      error.setPath(request.getRequestURI()); // Hata oluşan yolu ayarlar.
      error.setMessage(exception.getMessage()); // Hata mesajını ayarlar.

      if(exception instanceof DisabledException){
         error.setStatus(401); 
      } else if (exception instanceof AccessDeniedException){
        error.setStatus(403); 
      }
     
      
return ResponseEntity.status(error.getStatus()).body(error);
   }
}
