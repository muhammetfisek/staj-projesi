package com.fisek.ws.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration // Bu anotasyon, bu sınıfın bir konfigürasyon sınıfı olduğunu belirtir.
@EnableWebSecurity // Web güvenliğini etkinleştirir.
@EnableMethodSecurity(prePostEnabled = true) // Yöntem bazında güvenlik kontrolünü etkinleştirir. prePostEnabled=true, @PreAuthorize ve @PostAuthorize anotasyonlarını kullanmamızı sağlar.
public class SecurityConfiguration {
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests((authentication)->
        // Belirli bir PUT isteği (/api/v1/users/{id}) için kullanıcıların doğrulanması gerekiyor
        authentication.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/api/v1/users/{id}")).authenticated().requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/api/v1/users/{id}")).authenticated().
        anyRequest().permitAll() // Diğer tüm istekler herkese açık ve erişilebilir
        );
        // HTTP Basic Authentication kullanılıyor
        http.httpBasic(httpBasic-> httpBasic.authenticationEntryPoint(new AuthEntryPoint()));
        http.csrf(csrf->csrf.disable());// CSRF (Cross-Site Request Forgery) koruması devre dışı bırakılıyor
        http.headers(headers-> headers.disable()); // Güvenlik başlıkları devre dışı bırakılıyor (Özellikle H2 veritabanı konsolu için)
        return http.build();// Yapılandırılmış HTTP Security nesnesini döndürür
        }

       //@Bean, Spring'de bir nesneyi tanımlayıp yönetmek için kullanılır.
    @Bean  // Şifreleme için BCryptPasswordEncoder bean'i tanımlanıyor
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    
}
