package com.fisek.ws.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fisek.ws.user.Kullanici;
import com.fisek.ws.user.KullaniciService;

@Service // Bu sınıfı bir Spring servisi olarak tanımlar ve Spring konteynerine kaydeder.
//bu sınıf, e-posta ile kullanıcıyı bulur, varsa döner, yoksa hata fırlatır.
public class AppUserDetailsService implements UserDetailsService{

    @Autowired // Spring'in KullaniciService bağımlılığını enjekte etmesini sağlar
    KullaniciService kullaniciService;

    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Kullanici inDB=kullaniciService.findByEmail(email); // E-posta ile veri tabanında bir kullanıcı arar.
        if(inDB==null){// Kullanıcı veri tabanında bulunamazsa, hata fırlatır.
            throw new UsernameNotFoundException(email +"is not found ");
        } // Kullanıcı bulunduysa, CurrentUser nesnesi olarak döner.
        return new CurrentUser(inDB) ;

    }
    
}
