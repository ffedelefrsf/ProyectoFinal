/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft;

import com.funesoft.model.Usuario;
import com.funesoft.repository.UsuarioRepository;
import com.funesoft.utilities.CurrentUser;
import com.funesoft.utilities.RolEnum;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 *
 * @author faust
 */
@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private AuthenticationEntryPoint authEntryPoint;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private static final String URL_FRONT = "http://localhost:4200";
    
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                final String enteredUsername = authentication.getName();
                final String enteredPassword = authentication.getCredentials().toString();
                final Usuario usuario = usuarioRepository.findByUsername(enteredUsername);
                UserDetails userDetails = usuario;
                if (userDetails != null && userDetails.getPassword().equals(enteredPassword)) {
                    CurrentUser.newInstance(usuario);
                    return new UsernamePasswordAuthenticationToken(enteredUsername, enteredPassword, userDetails.getAuthorities());
                } else {
                    throw new BadCredentialsException("Bad Credentials - Funesoft");
                }
            }

            @Override
            public boolean supports(Class<?> type) {
                return type.equals(UsernamePasswordAuthenticationToken.class);
            }
        });
    }
 
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
          .csrf().disable()
          .authorizeRequests()
//          .antMatchers("/admin/**").hasRole("ADMIN")
//          .antMatchers("/anonymous*").anonymous()
          .antMatchers(HttpMethod.OPTIONS).permitAll()
          .antMatchers("/**").hasAuthority(RolEnum.ADMIN.toString())
//          .antMatchers("/**").permitAll()
          .and().httpBasic()
          .authenticationEntryPoint(authEntryPoint);
//          .anyRequest().authenticated()
//          .and()
//          .formLogin()
//          .loginPage("/login.html")
//          .loginProcessingUrl("/perform_login")
//          .defaultSuccessUrl("/homepage.html", true)
          //.failureUrl("/login.html?error=true")
//          .failureHandler(authenticationFailureHandler())
//          .and()
//          .logout()
//          .logoutUrl("/perform_logout")
//          .deleteCookies("JSESSIONID");
    }
     
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Component
    public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) throws IOException {
            if (request.getHeader("Origin") == null || !request.getHeader("Origin").equals(URL_FRONT))
                response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName()); // MUESTRA EL DIALOGO DE LOGEO EN LA WEB
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter writer = response.getWriter();
            writer.println("HTTP Status 401 - " + authEx.getMessage());
        }
        
        @Override
        public void afterPropertiesSet(){
            setRealmName("Funesoft");
            super.afterPropertiesSet();
        }
    }
    
}
