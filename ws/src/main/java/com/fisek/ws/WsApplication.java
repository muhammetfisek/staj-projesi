package com.fisek.ws;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fisek.ws.user.Kullanici;
import com.fisek.ws.user.KullaniciRepository;

@SpringBootApplication
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}
	@Bean
	@Profile("dev")
	CommandLineRunner useCreator(KullaniciRepository kullaniciRepository,PasswordEncoder passwordEncoder){
		
		return (args) ->{ // CommandLineRunner bean'i oluşturuluyor. Bu bean, uygulama başlatıldığında çalıştırılacak bir fonksiyon sağlar.
				for(var i=1;i<=25;i++){ // for kullanarak 25 e kadar kullanıcıları listeliyoruz
					Kullanici kullanici = new Kullanici();
					kullanici.setAd("user"+i);
					kullanici.setEmail("user"+i+ "@gmail.com");
					kullanici.setSifre(passwordEncoder.encode("P4ssword"));
					kullanici.setActive(true);
					kullaniciRepository.save(kullanici);

				}
		            

		};
	}

}
