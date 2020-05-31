package com.store.config;

import com.store.areas.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfig(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userService).passwordEncoder(this.bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers("/products/create").hasAnyAuthority("USER", "ADMIN")
                    .antMatchers("/categories/create" ,"/news/*").hasAnyAuthority("ADMIN")
                    .antMatchers("/", "/register", "/js/**", "/css/**", "/img/**", "/contact", "/about", "/product/**").permitAll()
                .and()
                    .formLogin().loginPage("/login").permitAll()
                    .usernameParameter("username")
                    .passwordParameter("password")
                .and()
                    .logout().logoutSuccessUrl("/login?logout").logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                .and()
                    .csrf()
                    .csrfTokenRepository(getCsrfTokenRepository())
                .and()
                    .rememberMe()
                    .rememberMeParameter("remember-me-new")
                    .key("35d1711e-1835-486b-b033-1c0d547f4f26")
                    .userDetailsService(this.userService)
                    .rememberMeCookieName("SPRING_SECURITY_REMEMBER_ME_COOKIE")
                    .tokenValiditySeconds(2400);
    }

    private CsrfTokenRepository getCsrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}