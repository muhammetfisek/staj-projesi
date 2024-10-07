package com.fisek.ws.auth.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.fisek.ws.shared.Messages;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(){
    super(Messages.getMessageForLocale("fisek.auth.invalid.credentials",LocaleContextHolder.getLocale()));
}}
