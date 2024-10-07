package com.fisek.ws.user.dto;

import com.fisek.ws.user.Kullanici;
import com.fisek.ws.user.validation.UniqueEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreate(
    @NotBlank(message="{fisek.constraint.ad.notblank}")
    //@Size(min=4,max=255 ) // min 4 max255 karakterden oluşabilir 
    String ad,

    @NotBlank
   @UniqueEmail
    @Email // email formatında olması gerektiği için 
    String email,
    
    //@Size(min=8,max=255)
    //@Pattern(regexp="^(?=.*[a-z]) (?=.*[A-Z]) (?=.*\\d).*$",message="{fisek.constraint.password.pattern}") // birden fazla büyük ve küçük harfve sayıdan olusması gerek  
    String sifre
) {
    public Kullanici toUser() {
        Kullanici kullanici = new Kullanici();
        kullanici.setEmail(email);
        kullanici.setAd(ad);
        kullanici.setSifre(sifre);
        return kullanici;
    }
    
    
}
