package com.fisek.ws.user;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  KullaniciRepository extends JpaRepository<Kullanici, Long> {
  
  Kullanici findByEmail(String email);// E-posta ile kullanıcıyı bulur.
  Kullanici findByActivationToken(String token);// Aktivasyon token'ı ile kullanıcıyı bulur.

  // Belirtilen ID hariç, kullanıcıları sayfalar halinde getirir.
  Page<Kullanici> findByIdNot(long id, Pageable page);
}
