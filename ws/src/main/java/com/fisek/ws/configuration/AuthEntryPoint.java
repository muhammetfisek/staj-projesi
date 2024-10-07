package com.fisek.ws.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//Bu sınıf, yetkilendirme hatalarını HandlerExceptionResolver kullanarak işler ve uygun hata yanıtını oluşturur.
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    @Qualifier("handlerExceptionResolver")// Spring’in HandlerExceptionResolver bean'ini enjekte eder
    private HandlerExceptionResolver exceptionResolver;

    @Override
    // Yetkilendirme hatası meydana geldiğinde bu metod çağrılır
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
      exceptionResolver.resolveException(request, response, null, authException);
    }
    
}
