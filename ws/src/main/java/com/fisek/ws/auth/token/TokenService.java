package com.fisek.ws.auth.token;
import com.fisek.ws.auth.dto.Credentials;
import com.fisek.ws.user.Kullanici;

public interface  TokenService {
    public Token createToken(Kullanici user,Credentials creds); //createToken, verilen kullanıcı ve kimlik bilgileriyle bir token oluşturur 
    public Kullanici verifyToken(String authorizationHeader);//verifyToken, yetkilendirme başlığını kullanarak token'ı doğrular ve ilgili kullanıcıyı döner
}
