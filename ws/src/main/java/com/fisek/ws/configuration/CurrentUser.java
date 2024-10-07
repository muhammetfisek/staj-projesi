package com.fisek.ws.configuration;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.fisek.ws.user.Kullanici;

//Bu sınıf, UserDetails arayüzünü uygulayarak kullanıcı bilgilerini sağlar. Kullanıcı adını, şifresini ve yetkilerini döner, ayrıca hesabın durumunu (aktif, kilitli, süresi dolmuş vs.) kontrol eder.
public class CurrentUser implements UserDetails {

    long id;
    String  ad;
    String sifre;
    boolean enabled;

    public CurrentUser(Kullanici kullanici){
this.id=kullanici.getId();
this.ad=kullanici.getAd();
this.sifre=kullanici.getSifre();
this.enabled=kullanici.isActive();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
               return AuthorityUtils.createAuthorityList("ROLE_KULLANİCİ");
            }

            @Override
            public String getPassword() {
               return this.sifre;
            }

            @Override
            public String getUsername() {
                return this.ad;
            }
            @Override
            public boolean isAccountNonExpired() {
                return true;
            }
            @Override
            public boolean isAccountNonLocked() {
                return true;
            }
            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }
            @Override
            public boolean isEnabled() {
                return enabled;
            }
    
}
