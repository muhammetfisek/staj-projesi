package com.fisek.ws.user;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fisek.ws.configuration.CurrentUser;
import com.fisek.ws.email.EmailService;
import com.fisek.ws.file.FileService;
import com.fisek.ws.user.dto.UserUpdate;
import com.fisek.ws.user.exception.ActivationNotificationException;
import com.fisek.ws.user.exception.InvalidTokenException;
import com.fisek.ws.user.exception.NotFoundException;
import com.fisek.ws.user.exception.NotUniqueEmailException;

import jakarta.transaction.Transactional;


@Service// sınıfımızı servis olarak kullanıyoruz onun için ekledik 
public class KullaniciService {
    

 @Autowired //@Autowired, Spring Framework'ün bağımlılık enjeksiyonu özelliğini kullanarak KullaniciRepository nesnesini otomatik olarak sınıfınıza ekler. Bu sayede, KullaniciRepository'yi manuel olarak oluşturmak zorunda kalmazsınız; Spring, gerekli bean'i sağlar ve bu alanı otomatik olarak doldurur.
  KullaniciRepository kullaniciRepository;

  @Autowired
PasswordEncoder passwordEncoder;

@Autowired
EmailService emailService;

@Autowired
FileService fileService;


@Transactional(rollbackOn=MailException.class)// eğer bir mailexception ile karşılaşırsa database yazılan verileri geri alır 
        public void save(Kullanici kullanici){
    // Kullanıcının şifresini alır ve güvenli bir biçimde şifreler.
    // 'passwordEncoder.encode' metodu, şifreyi güvenli bir şekilde hash(veriyi güvenli ve sabit uzunlukta bir diziye dönüştürür.)'ler.
    try {
        String encodeSifre =passwordEncoder.encode(kullanici.getSifre());  // Şifrelenmiş şifreyi Kullanici nesnesine geri atar.
        kullanici.setSifre(encodeSifre);
        kullaniciRepository.saveAndFlush(kullanici);   // 'kullaniciRepository.save' metodu, kullanıcıyı veritabanına ekler veya mevcut kullanıcıyı günceller.
        kullanici.setActivationToken(UUID.randomUUID().toString());
emailService.sendActivationEmail(kullanici.getEmail(),kullanici.activationToken);

    } catch (DataIntegrityViolationException ex ) {
        throw new NotUniqueEmailException();
    }
catch(MailException ex){
    throw new ActivationNotificationException();}}

     public void activateUser(String token) {  // Veritabanında verilen aktivasyon token'ına sahip kullanıcıyı bulur
        Kullanici inDB=kullaniciRepository.findByActivationToken(token);
        if(inDB==null){ // Eğer kullanıcı bulunamazsa, geçersiz token hatası fırlatır
            throw new InvalidTokenException();
        }
        inDB.setActive(true);
        inDB.setActivationToken(null);
        kullaniciRepository.save(inDB); // Güncellenmiş kullanıcı bilgilerini veritabanına kaydeder
    }

     public Page<Kullanici> getUsers(Pageable page,CurrentUser currentUser) { // Pageable ile sayfalama bilgilerini alır ve Kullanici nesnelerini döndürür.
        if(currentUser==null){
             return kullaniciRepository.findAll(page); // Veritabanından tüm kullanıcıları sayfalı olarak döner.
         }
         return kullaniciRepository.findByIdNot(currentUser.getId(), page);
       
    }
    public Kullanici getUser(long id ){// Veritabanında belirtilen ID ile kullanıcıyı arar
    //orElseThrow(): Eğer kullanıcı bulunamazsa, NotFoundException fırlatır ve id'yi hata mesajına dahil eder.
      return kullaniciRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Kullanici findByEmail(String email) {  // E-posta ile kullanıcıyı veritabanından bulur.
        return kullaniciRepository.findByEmail(email);
    }

    public Kullanici updateUser(long id, UserUpdate userUpdate) {
       Kullanici inDB = getUser(id); // Belirtilen ID ile kullanıcıyı alır ve günceller.
       inDB.setAd(userUpdate.ad());
       if(userUpdate.image() != null){ // Eğer güncellenen kullanıcının bir resmi varsa
        // Base64 formatındaki resmi dosya olarak kaydeder ve dosya adını alır
          String fileName=fileService.saveBase64StringAsFile(userUpdate.image());
          fileService.deleteProfileImage(inDB.getImage());// Veritabanında mevcut olan eski profil resmini siler
          inDB.setImage(fileName);// Yeni dosya adını veritabanındaki kullanıcıya kaydeder
       }
     
       return kullaniciRepository.save(inDB); // Güncellenmiş kullanıcıyı veritabanına kaydeder.
    }

    public void deleteUser(long id) {
        kullaniciRepository.deleteById(id);
    }
  
    
}