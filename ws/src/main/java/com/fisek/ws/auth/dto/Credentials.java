package com.fisek.ws.auth.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record Credentials( @Email String email, @NotBlank String sifre) { // email formatında ve sıfre bos olmayacak
    
}
 