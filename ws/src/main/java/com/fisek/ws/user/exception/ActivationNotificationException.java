package com.fisek.ws.user.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.fisek.ws.shared.Messages;

public class ActivationNotificationException  extends RuntimeException{
    public ActivationNotificationException(){
        super(Messages.getMessageForLocale("fisek.create.user.email.failure", LocaleContextHolder.getLocale()));
    }
    
}
