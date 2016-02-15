package api.security;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService restUserDetailsService;

    @Autowired
    private AuthenticationSuccessHandler authSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(authSuccessHandler)
                .permitAll()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint())

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .enableSessionUrlRewriting(false)

                .and()
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(restAuthenticationProvider());
    }

    @Bean
    public StandardPBEStringEncryptor tokenEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
        encryptor.setPassword("H_q4700289T!5A");
        return encryptor;
    }

    private AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    private AuthenticationProvider restAuthenticationProvider() {
        RestAuthenticationProvider authenticationProvider = new RestAuthenticationProvider();
        authenticationProvider.setUserDetailsService(restUserDetailsService);
        return authenticationProvider;
    }
}
