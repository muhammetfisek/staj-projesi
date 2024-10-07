package com.fisek.ws.user.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.fisek.ws.shared.Messages;

public class InvalidTokenException extends RuntimeException{
        // Geçersiz token hatası oluşturur
    public InvalidTokenException(){ // Hata mesajını kullanıcının diline göre alır ve üst sınıfa iletir
        super(Messages.getMessageForLocale("fisek.activate.user.invalid.token", LocaleContextHolder.getLocale()));
    }
    
}
