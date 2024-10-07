package com.fisek.ws.auth.dto;

import com.fisek.ws.auth.token.Token;
import com.fisek.ws.user.dto.UserDTO;
public class AuthResponse {
    UserDTO user; 
    Token token;

    
    public UserDTO getUser() {
        return user;
    }
    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Token getToken() {
        return token;
    }
    public void setToken(Token token) {
        this.token = token;
    }
}
