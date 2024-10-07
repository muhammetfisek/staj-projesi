package com.fisek.ws.user.dto;

import com.fisek.ws.user.Kullanici;

public class UserDTO {

    private long id;
    private String ad;
    private String email;
    private String image;

    // Kullanici nesnesinden UserDTO oluşturur
    public UserDTO(Kullanici kullanici) {
        this.id = kullanici.getId(); // ID'yi doğrudan atar
        this.ad = kullanici.getAd(); // Ad'yi doğrudan atar
        this.email = kullanici.getEmail(); // Email'i doğrudan atar
        this.image = kullanici.getImage(); // Image'ı doğrudan atar
    }

    // Getter ve setter metodları
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
