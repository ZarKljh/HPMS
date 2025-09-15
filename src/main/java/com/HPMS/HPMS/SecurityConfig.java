package com.HPMS.HPMS;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomLoginSuccessHandler customLoginSuccessHandler;

    public SecurityConfig(CustomLoginSuccessHandler customLoginSuccessHandler) {
        this.customLoginSuccessHandler = customLoginSuccessHandler;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll())

                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/hpms/login", "/hpms/signup", "/error").permitAll()
                        .requestMatchers("/CSS/**", "/js/**", "/img/**").permitAll()
                        .anyRequest().authenticated())
                /*
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**").permitAll())
                */
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/h2-console/**"))
//                .csrf((csrf) -> csrf
//                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
//                .formLogin((formLogin) -> formLogin
//                        .loginPage("/hpms/login")
//                        .defaultSuccessUrl("/"))
                .formLogin((formLogin) -> formLogin
                        .loginPage("/hpms/login")         // 로그인 폼 경로
                        //.loginProcessingUrl("/hpms/login") // 로그인 처리 경로 (POST)
                        .successHandler(customLoginSuccessHandler)
                        //.defaultSuccessUrl("/", true)          // 로그인 성공 후 이동 경로
                        .permitAll())
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/hpms/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))


//                .logout((logout) -> logout
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/hpms/logout"))
//                        .logoutSuccessUrl("/hpms/login")
//                        .invalidateHttpSession(true))
////                사용자가 권한부족으로 페이지접속이 거부되는 경우
//                .exceptionHandling(exception -> exception
//                        .accessDeniedHandler(new CustomAccessDeniedHandler())
//                );
        ;
        return http.build();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

/**/
