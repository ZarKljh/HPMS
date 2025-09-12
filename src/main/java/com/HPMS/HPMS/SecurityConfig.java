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
                        /*.loginProcessingUrl("/hpms/login") // 로그인 처리 경로 (POST)*/
                        .defaultSuccessUrl("/", true)          // 로그인 성공 후 이동 경로
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
/*package com.HPMS.HPMS;

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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/hpms/login",         // 로그인 페이지
                                "/hpms/logout",        // 로그아웃 요청
                                "/h2-console/**",      // H2 콘솔
                                "/css/**", "/js/**", "/img/**" // 정적 리소스
                        ).permitAll()
                        .anyRequest().authenticated() // 그 외는 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/hpms/login")
                        .defaultSuccessUrl("/", true) // 로그인 성공 시 리디렉션
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/hpms/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") // H2 콘솔은 CSRF 제외
                )
                .headers(headers -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)) // H2 콘솔 프레임 허용
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/error") // 권한 부족 시 에러 페이지
                );

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
}*/
/*package com.HPMS.HPMS;

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
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                .formLogin((formLogin) -> formLogin
                        .loginPage("/hpms/login")
                        .defaultSuccessUrl("/"))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/hpms/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))
//                사용자가 권한부족으로 페이지접속이 거부되는 경우
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
}*/

/**/
