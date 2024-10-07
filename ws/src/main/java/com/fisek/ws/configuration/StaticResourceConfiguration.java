package com.fisek.ws.configuration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class StaticResourceConfiguration  implements WebMvcConfigurer{

@Autowired
 FisekProperties fisekProperties;

 // Statik kaynaklar için gerekli olan dosya yollarını yapılandırır
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry){
     // Dosya depolama yolunu alır ve mutlak bir yol haline getirir
     String path = Paths.get(fisekProperties.getStorage().getRoot()).toAbsolutePath().toString() + "/";
    registry.addResourceHandler("/assets/**") // "/assets/**" URL'lerine gelen istekleri dosya sistemindeki bu yoldaki dosyalarla eşleştirir
     // Dosya sisteminden gelen dosyaların kökünü belirtir
    .addResourceLocations("file:" + path) // Tarayıcıda 365 gün önbellekleme yapılmasını sağlar
    .setCacheControl(CacheControl.maxAge(365,TimeUnit.DAYS));
}
     
@Bean
CommandLineRunner createStorageDirs(){
    return args->{// Kök klasörü oluşturur
        createFolder(Paths.get(fisekProperties.getStorage().getRoot())); // Profil resimlerinin saklanacağı klasörü oluşturur
        createFolder(Paths.get(fisekProperties.getStorage().getRoot(), fisekProperties.getStorage().getProfile()));
    };
    }

     // Verilen yol için klasör oluşturur (eğer yoksa)
    private void createFolder(Path path){
        File file=path.toFile();
        boolean isFolderExist=file.exists() && file.isDirectory(); // Klasörün var olup olmadığını kontrol eder
        if(!isFolderExist){ // Klasör yoksa yeni bir klasör oluşturur
            file.mkdir();
        }
    }


}
