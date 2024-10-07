package com.fisek.ws.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fisek.ws.configuration.FisekProperties;

@Service
public class FileService {

    @Autowired
    FisekProperties fisekProperties;

    Tika tika = new Tika(); // Dosya türünü algılamak için Apache Tika kullanılır

    // Base64 formatındaki bir resmi dosya olarak kaydeder ve dosya adını döndürür
    public String saveBase64StringAsFile(String image) {

      String filename = UUID.randomUUID().toString(); // Benzersiz bir dosya adı oluşturur
      Path path = getProfileImagePath(filename); // Resmin kaydedileceği dosya yolunu alır
      
       try {
            OutputStream outputStream = new FileOutputStream(path.toFile()); // Dosya yazma işlemi için OutputStream açar
            outputStream.write(decodedImage(image)); // Base64'ten çözülmüş resmi dosyaya yazar
            outputStream.close(); // Dosya yazma işlemini tamamlar
            return filename; // Başarılı olursa dosya adını döndürür
        } catch (IOException e) {
            e.printStackTrace(); // Hata varsa hata mesajını yazdırır
        }
        return null; // Başarısız olursa null döner
    }

     // Base64 string'inin dosya türünü algılar
    public String detectType(String value) {
       return tika.detect(decodedImage(value));// Base64 çözülmüş verinin dosya türünü döndürür
    }
    
    // Base64 kodlu resmi çözer ve byte dizisine dönüştürür
    private byte[] decodedImage(String encodedImage){
      // Base64 string'ini çözerek byte dizisine çevirir
       return  Base64.getDecoder().decode(encodedImage.split(",")[1]);
    }



    // Verilen dosya adıyla ilgili profil resmini siler
    public void deleteProfileImage(String image) {
        if (image == null) return; // Resim yoksa işlemi sonlandırır
        Path path = getProfileImagePath(image); // Silinecek dosya yolunu alır
        try {
            Files.deleteIfExists(path); // Dosya mevcutsa siler
        } catch (IOException e) {
            e.printStackTrace(); // Hata varsa hata mesajını yazdırır
        }
    }

   // Dosya adını alarak profil resminin kaydedileceği yolu oluşturur
    private Path getProfileImagePath(String filename) {
        return Paths.get(fisekProperties.getStorage().getRoot(), fisekProperties.getStorage().getProfile(), filename); // Kök dizin ve profil dizinini birleştirir
    }
}
