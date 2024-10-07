package com.fisek.ws.user;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;


@Table (name="kullanicilar", uniqueConstraints=@UniqueConstraint(columnNames={"email"})) // tablo adımızı girdik ve email sutununda benzersizlik kısıtlamsı
 @Entity //Spring Data JPA bunu kullanarak tablo yaratır
public class Kullanici {//Getter, bir değişkenin değerini okumak; setter ise bu değeri değiştirmek için kullanılır
    @Id
    @GeneratedValue //bu anahtarın otomatik olarak artmasını sağlar 
    long id;

    String ad;

    String email;

    @JsonIgnore // görünmesini istemediğimiz için bunu ekledk
    String sifre;

    @JsonIgnore
    boolean active =false;

    @JsonIgnore
    String activationToken;

    @Lob //large objedir
    String image;

    String passwordResetToken;

    public String getPasswordResetToken() {
        return passwordResetToken;
    }
    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getActivationToken() {
        return activationToken;
    }
    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public long getId() {
        return id;
    }
    public  void setId(long id) {
        this.id = id;
    }
  
    public String getAd() {
        return ad;
    }
    public void setAd(String ad) {
        this.ad = ad;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSifre() {
        return sifre;
    }
    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}