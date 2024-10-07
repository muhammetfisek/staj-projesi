package com.fisek.ws.user.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.fisek.ws.shared.Messages;


// Belirtilen id'ye göre özelleştirilmiş bir hata mesajı oluşturur
public class NotFoundException extends  RuntimeException{
    public NotFoundException (long id ){ // Hata mesajını locale'e göre çeker ve Exception mesajına geçirir
        super(Messages.getMessageForLocale("fisek.user.not.found", LocaleContextHolder.getLocale(),id));
    }
}
