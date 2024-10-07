package com.fisek.ws.user.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.fisek.ws.user.Kullanici;
import com.fisek.ws.user.KullaniciRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String>{ //UniqueEmailValidator sınıfı, ConstraintValidator arayüzünü uygular.
    @Autowired  // Spring'in bağımlılık enjeksiyonu için kullanılır.
KullaniciRepository kullaniciRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) { // isValid metodu, email adresinin benzersiz olup olmadığını kontrol eder
       Kullanici inDB= kullaniciRepository.findByEmail(value); // Veritabanında email adresini arar.
       return  inDB==null; // Email adresi veritabanında yoksa true döner, varsa false döner.
    }
    
}
