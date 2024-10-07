package com.fisek.ws.error;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fisek.ws.shared.Messages;
import com.fisek.ws.user.exception.ActivationNotificationException;
import com.fisek.ws.user.exception.InvalidTokenException;
import com.fisek.ws.user.exception.NotFoundException;
import com.fisek.ws.user.exception.NotUniqueEmailException;

import jakarta.servlet.http.HttpServletRequest;





@RestControllerAdvice
public class ErrorHandler {
    
    @ExceptionHandler (MethodArgumentNotValidException.class)
ResponseEntity <ApiError> handleMethodArgNotValidEx( MethodArgumentNotValidException exception,HttpServletRequest request) 
{
    ApiError apiError=new ApiError();
    apiError.setPath(request.getRequestURI());
    String message =Messages.getMessageForLocale("fisek.error.validation",LocaleContextHolder.getLocale());
apiError.setMessage(message);
apiError.setStatus(400);
Map<String, String> validationErrors=new HashMap<>();
for(var fieldError: exception.getBindingResult().getFieldErrors()){
    validationErrors.merge(fieldError.getField(),fieldError.getDefaultMessage(),(existing, replacing) -> existing);
}
apiError.setValidationErrors(validationErrors);
return ResponseEntity.badRequest().body(apiError);}
 


    @ExceptionHandler(NotUniqueEmailException.class) // NotUniqueEmailException sınıfı için bir istisna işleyici tanımlar.
     ResponseEntity<ApiError> handleNotUniqueEmailEx(NotUniqueEmailException exception) { 
        ApiError apiError = new ApiError(); // Yeni bir ApiError nesnesi oluşturur.
        apiError.setPath("/api/v1/users"); // Hata oluşan yolu ayarlar.
        apiError.setMessage(exception.getMessage()); // Hata mesajını ayarlar.
        apiError.setStatus(400); // HTTP durum kodunu 400 (Bad Request) olarak ayarlar.
        apiError.setValidationErrors(exception.getValidationErrors()); // ApiError nesnesine doğrulama hatalarını ekler. 
        return ResponseEntity.status(400).body(apiError); // HTTP 400 yanıtı ile ApiError nesnesini döner.
    }

    @ExceptionHandler(ActivationNotificationException.class) // NotUniqueEmailException sınıfı için bir istisna işleyici tanımlar.
    ResponseEntity<ApiError> handleActivationNotificationException(ActivationNotificationException exception) { 
       ApiError apiError = new ApiError(); // Yeni bir ApiError nesnesi oluşturur.
       apiError.setPath("/api/v1/users"); // Hata oluşan yolu ayarlar.
       apiError.setMessage(exception.getMessage()); // Hata mesajını ayarlar.
       apiError.setStatus(502); // HTTP durum kodunu 400 (Bad Request) olarak ayarlar.
       
       return ResponseEntity.status(502).body(apiError); // HTTP 400 yanıtı ile ApiError nesnesini döner.
   }

   @ExceptionHandler(InvalidTokenException.class) // NotUniqueEmailException sınıfı için bir istisna işleyici tanımlar.
   ResponseEntity<ApiError> handleInvalidTokenException(InvalidTokenException exception,HttpServletRequest request) { 
      ApiError apiError = new ApiError(); // Yeni bir ApiError nesnesi oluşturur.
      apiError.setPath(request.getRequestURI()); // Hata oluşan yolu ayarlar.
      apiError.setMessage(exception.getMessage()); // Hata mesajını ayarlar.
      apiError.setStatus(400); // HTTP durum kodunu  (Bad Request) olarak ayarlar.
      
      return ResponseEntity.status(400).body(apiError); // HTTP 400 yanıtı ile ApiError nesnesini döner.
  }

  @ExceptionHandler(NotFoundException.class) // NotUniqueEmailException sınıfı için bir istisna işleyici tanımlar.
   ResponseEntity<ApiError> handleNotFoundException(NotFoundException exception,HttpServletRequest request) { 
      ApiError apiError = new ApiError(); // Yeni bir ApiError nesnesi oluşturur.
      apiError.setPath(request.getRequestURI()); // Hata oluşan yolu ayarlar.
      apiError.setMessage(exception.getMessage()); // Hata mesajını ayarlar.
      apiError.setStatus(404); 
      
return ResponseEntity.status(404).body(apiError); // HTTP 404 yanıtı ile ApiError nesnesini döner.
  }


}
