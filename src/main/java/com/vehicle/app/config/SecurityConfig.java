package com.vehicle.app.config;

import com.vehicle.app.entity.Role;
import com.vehicle.app.entity.User;
import com.vehicle.app.enums.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;
    @Autowired
    @Qualifier("inMemoryUserDetailsService")
    private UserDetailsService inMemoryUserDetailsService;

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        log.info("PasswordEncoder bean initialized");
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().disable()
                .headers().frameOptions().disable().and()
                .authorizeRequests()
                .antMatchers("/h2-console", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .userDetailsService(inMemoryUserDetailsService)
                .passwordEncoder(passwordEncoder).setBuilder(auth);
    }

    @Bean
    @Primary
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    
    @Override
    @Bean("inMemoryUserDetailsService")
    public UserDetailsService userDetailsService() {
        User user = new User();
        user.setUsername("superadmin");
        user.setActive(true);
        user.setName("Super Admin");
        user.setRoleAlisa(Roles.SUPERADMIN.getAlisa());
        user.setRoles(Stream.of(new Role(Roles.SUPERADMIN.getId(), Roles.SUPERADMIN.name(), true)).collect(Collectors.toSet()));
        user.setPassword(passwordEncoder.encode("superadmin"));
        return new InMemoryUserDetailsManagerV1(user);
    }

    class InMemoryUserDetailsManagerV1 extends InMemoryUserDetailsManager {

        public InMemoryUserDetailsManagerV1(UserDetails... users) {
            super(users);
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            boolean isExists = this.userExists(username.toLowerCase());
            if (isExists) {
                User user = new User();
                user.setUsername("superadmin");
                user.setActive(true);
                user.setName("Super Admin");
                user.setRoleAlisa(Roles.SUPERADMIN.getAlisa());
                user.setRoles(Stream.of(new Role(Roles.SUPERADMIN.getId(), Roles.SUPERADMIN.name(), true)).collect(Collectors.toSet()));
                user.setPassword(passwordEncoder.encode("superadmin"));
                return user;
            } else {
                throw new UsernameNotFoundException(username);
            }
        }
    }
}