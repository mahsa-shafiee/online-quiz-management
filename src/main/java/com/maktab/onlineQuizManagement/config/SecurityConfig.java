package com.maktab.onlineQuizManagement.config;

import com.maktab.onlineQuizManagement.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    @Order(1)
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatcher(new AntPathRequestMatcher("/adminPanel/**"))
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/adminPanel/**").access("hasRole('ROLE_ADMIN')")
                    .and()
                    .formLogin()
                    .loginPage("/adminPanel/login")
                    .permitAll()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successForwardUrl("/adminPanel/login")
                    .and()
                    .logout()
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/signOut"))
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true);
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                    .withUser("mahsa")
                    .password(passwordEncoder().encode("1234"))
                    .roles("ADMIN");
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @Order(2)
    @Configuration
    public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {
        private final UserService userService;
        private final PasswordEncoder passwordEncoder;

        public UserSecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
            this.userService = userService;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatcher(new AntPathRequestMatcher("/userPanel/**"))
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/userPanel/**").access("hasRole('ROLE_TEACHER')")
                    .and()
                    .formLogin()
                    .loginPage("/userPanel/login")
                    .permitAll()
                    .usernameParameter("emailAddress")
                    .passwordParameter("password")
                    .successForwardUrl("/userPanel/login")
                    .and()
                    .logout()
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/signOut"))
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true);
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
        }

    }

}
