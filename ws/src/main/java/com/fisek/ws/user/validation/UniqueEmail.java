package com.fisek.ws.user.validation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
//NOTBLANK TEN ALDIM 
@Constraint(validatedBy = UniqueEmailValidator.class) //email alanının benzersiz olmasını sağlamak için kullanılır.
@Target({ElementType.FIELD }) //alan seviyesinde uygulanır.
@Retention(RetentionPolicy.RUNTIME) // çalışma zamanında kullanılabilir.
public @interface UniqueEmail { // burda yazanları notblank ten aldım 
    String message() default "{fisek.constraint.email.notunique}"; //Email zaten kullanılıyorsa gösterilecek hata mesajı
    Class<?>[] groups() default { };// Gruplar, doğrulama sırasında uygulanan kısıtlamaların setini sınırlamak için kullanılabilir.
    Class<? extends Payload>[] payload() default { }; // Payload, Bean Validation API'sinin müşterileri tarafından bir kısıtlamaya özel yük nesneleri atamak için kullanılabilir.
}
