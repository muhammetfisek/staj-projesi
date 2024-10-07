package com.fisek.ws.user.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = FileTypeValidator.class) //bir alanın dosya türünü (JPEG, PNG gibi) kontrol eder. Geçerli değilse hata mesajı gösterir.
@Target({ElementType.FIELD }) //alan seviyesinde uygulanır.
@Retention(RetentionPolicy.RUNTIME) // çalışma zamanında kullanılabilir
public @interface FileType {

     String message() default "Only {types} are allowed"; //Email zaten kullanılıyorsa gösterilecek hata mesajı
    Class<?>[] groups() default { };// Gruplar, doğrulama sırasında uygulanan kısıtlamaların setini sınırlamak için kullanılabilir.
    Class<? extends Payload>[] payload() default { };
     
    String[] types();
}
