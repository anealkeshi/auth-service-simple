package com.anilkc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.anilkc.config.handler.CustomAccessDeniedHandler;
import com.anilkc.config.handler.CustomAuthenticationEntryPoint;
import com.anilkc.config.handler.CustomLoginFailureHandler;
import com.anilkc.config.handler.CustomLoginSuccessfulHandler;
import com.anilkc.config.handler.CustomLogoutSuccessfulHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


  @Autowired
  private CustomLoginSuccessfulHandler loginSuccessfulHandler;

  @Autowired
  private CustomLoginFailureHandler loginFailureHandler;

  @Autowired
  private CustomLogoutSuccessfulHandler logoutSuccessfulHandler;

  @Autowired
  private CustomAccessDeniedHandler customAccessDeniedHandler;

  @Autowired
  private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  //@formatter:off
    auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("ADMIN");
  // @formatter:on

  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

  //@formatter:off
      http
      .csrf().disable()
          .formLogin()
              .loginProcessingUrl("/auth/login")
              .successHandler(loginSuccessfulHandler)
              .failureHandler(loginFailureHandler)
          .and()
              .logout()
              .logoutUrl("/auth/logout")
              .logoutSuccessHandler(logoutSuccessfulHandler)
          .and()
            .authorizeRequests()
            .antMatchers("/auth/login").permitAll()
            .antMatchers("/secure/admin").access("hasRole('ADMIN')")
            .anyRequest().authenticated()
           .and()
             .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
             .authenticationEntryPoint(customAuthenticationEntryPoint)
          .and()
            .anonymous()
              .disable();
  // @formatter:on
  }
  
}
