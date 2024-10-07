package com.fisek.ws.user.validation;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.fisek.ws.file.FileService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileTypeValidator implements ConstraintValidator<FileType, String>{


    @Autowired
    FileService FileService;

    String[] types;

@Override
public void initialize(FileType constraintAnnotation){
    // Anotasyon ile belirtilen geçerli dosya türlerini alır
   this.types= constraintAnnotation.types();
}

    @Override   // Dosyanın geçerli olup olmadığını kontrol eder
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Eğer değer null ya da boş ise geçerli kabul edilir
        if(value==null || value.isEmpty()) return true;
        String type = FileService.detectType(value); // Dosyanın türünü tespit eder
         for(String validType : types){ // Tespit edilen dosya türünün kabul edilen türlerle uyumlu olup olmadığını kontrol eder
            if(type.contains(validType)) return true;// Uygun dosya türü bulunduysa geçerli kabul edilir
         }
          // Kabul edilen dosya türlerini virgülle ayırarak bir string haline getirir
         String validTypes=Arrays.stream(types).collect(Collectors.joining(","));
         context.disableDefaultConstraintViolation(); 

         //Bu bölüm, geçersiz dosya tipi olduğunda varsayılan hata mesajını devre dışı bırakır ve kabul edilen dosya türlerini belirten özel bir hata mesajı oluşturur. Böylece kullanıcıya, hangi dosya türlerinin geçerli olduğunu gösterecek şekilde doğrulama mesajı sunulur.
         HibernateConstraintValidatorContext hibernateConstraintValidatorContext=context.unwrap(HibernateConstraintValidatorContext.class);
         hibernateConstraintValidatorContext.addMessageParameter("types", validTypes);
         hibernateConstraintValidatorContext.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();
        return false;
    }
    
}
