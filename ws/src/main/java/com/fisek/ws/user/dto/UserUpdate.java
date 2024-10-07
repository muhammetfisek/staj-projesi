package com.fisek.ws.user.dto;

import com.fisek.ws.user.validation.FileType;

import jakarta.validation.constraints.NotBlank;

public record  UserUpdate(  
@NotBlank(message="{fisek.constraint.ad.notblank}")
   String ad,

   @FileType(types={"jpeg" , "png"})
   String image
   
   ) {
   
    
}
