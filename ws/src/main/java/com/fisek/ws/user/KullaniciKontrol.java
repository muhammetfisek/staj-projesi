package com.fisek.ws.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fisek.ws.configuration.CurrentUser;
import com.fisek.ws.shared.GenelMesaj;
import com.fisek.ws.shared.Messages;
import com.fisek.ws.user.dto.UserCreate;
import com.fisek.ws.user.dto.UserDTO;
import com.fisek.ws.user.dto.UserUpdate;

import jakarta.validation.Valid;



@RestController //HTTP isteklerine yanıt olarak veri döndürmek için kullanılır.
public class KullaniciKontrol {
    @Autowired //@Autowired, Spring Framework'ün bağımlılık enjeksiyonu özelliğini kullanarak KullaniciService nesnesini otomatik olarak sınıfınıza ekler. Bu sayede, KullaniciService'yi manuel olarak oluşturmak zorunda kalmazsınız; Spring, gerekli bean'i sağlar ve bu alanı otomatik olarak doldurur.
    KullaniciService kullaniciService;



   

    @PostMapping("/api/v1/users") // bu metodu HTTP POST istekleri için bir endpoint olarak tanımlamasını sağlar.
    ///api/v1/users URL yoluna gelen POST isteklerini bu metoda yönlendirir.
     GenelMesaj kullaniciOlustur( @Valid @RequestBody UserCreate kullanici){  //@RequestBody, HTTP isteğinin gövdesindeki veriyi bir metoda parametre olarak alır.
        kullaniciService.save(kullanici.toUser()); //Gelen Kullanici nesnesini veritabanına kaydeder.
        String message =Messages.getMessageForLocale("fisek.create.user.succes.message", LocaleContextHolder.getLocale());
        return new GenelMesaj(message);  // İşlem başarılı olduğunda kullanıcıya geri dönülecek mesajı oluşturur.
}

@PatchMapping("/api/v1/users/{token}/active") //@PatchMapping anotasyonu, HTTP PATCH isteklerini belirli bir metoda yönlendirmek için kullanılır.
GenelMesaj activateUser(@PathVariable String token){  // Kullanıcıyı verilen token ile aktif hale getirir
     kullaniciService.activateUser(token);  // Kullanıcının diline göre başarı mesajını alır
    String message =Messages.getMessageForLocale("fisek.activate.user.succes.message", LocaleContextHolder.getLocale());
    return new GenelMesaj(message);   // Başarı mesajını içeren bir GenelMesaj nesnesi döner
}

@GetMapping("/api/v1/users") // "/api/v1/users" URL'sine gelen GET isteklerini işler.
Page<UserDTO> getUsers(Pageable page,@AuthenticationPrincipal CurrentUser currentUser){ // Pageable ile sayfalama bilgilerini alır ve UserDTO nesnelerini döndürür
     // Kullanıcıları sayfalı olarak döner
   
    return kullaniciService.getUsers(page,currentUser).map(UserDTO::new); // Her User nesnesini UserDTO'ya dönüştürür.
}

@GetMapping("/api/v1/users/{id}")
UserDTO getUserById( @PathVariable long id ){ // Kullanıcının ID'sine göre kullanıcıyı alır ve UserDTO'ya dönüştürerek geri döner
//@PathVariable: URL'deki {id} kısmını id değişkenine atar.
return  new UserDTO(kullaniciService.getUser(id));
}


//Bu kod, belirtilen kullanıcı ID'si ile kullanıcı bilgilerini günceller ve sadece kullanıcı kendi bilgilerini güncelleyebilir
@PutMapping ("/api/v1/users/{id}")// Belirli bir kullanıcı ID'si ile kullanıcı bilgilerini güncellemek için PUT isteği tanımlar.
@PreAuthorize("#id==principal.id") // Sadece kullanıcı, kendi bilgilerini güncelleyebilir. Kullanıcı kimliği istekteki ID ile eşleşmelidir.
UserDTO uptadeUser(@PathVariable long id ,@Valid @RequestBody UserUpdate userUpdate){
// Kullanıcı bilgilerini günceller ve güncellenmiş bilgileri içeren bir UserDTO döner.
    return new UserDTO(kullaniciService.updateUser(id,userUpdate));
}


@DeleteMapping ("/api/v1/users/{id}")// Belirli bir kullanıcı ID'si ile kullanıcı bilgilerini silmek
@PreAuthorize("#id==principal.id") // Sadece kullanıcı, kendi bilgilerini güncelleyebilir. Kullanıcı kimliği istekteki ID ile eşleşmelidir.
GenelMesaj deleteUser(@PathVariable long id ){
kullaniciService.deleteUser(id);
  return new GenelMesaj("Kullanici Silindi");
}

} 