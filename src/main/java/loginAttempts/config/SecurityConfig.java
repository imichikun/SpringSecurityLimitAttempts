package loginAttempts.config;

import loginAttempts.security.CustomLoginFailureHandler;
import loginAttempts.security.CustomLoginSuccessHandler;
import loginAttempts.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final PersonDetailsService personDetailsService;
    private final CustomLoginFailureHandler loginFailureHandler;
    private  final CustomLoginSuccessHandler loginSuccessHandler;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService, CustomLoginFailureHandler loginFailureHandler,
                          CustomLoginSuccessHandler loginSuccessHandler) {
        this.personDetailsService = personDetailsService;
        this.loginFailureHandler = loginFailureHandler;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public DefaultSecurityFilterChain configurer(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/auth/login", "/error", "/auth/register", "/auth/finishRegistration").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                    .failureHandler(loginFailureHandler)
                    .successHandler(loginSuccessHandler)
                    .usernameParameter("username")
                    .permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");

//        http
////                .csrf().disable()
//                .authorizeRequests()
//                .requestMatchers("/auth/login", "/error", "/auth/register", "/auth/finishRegistration").permitAll()
//                .anyRequest().authenticated()
//                .anyRequest().hasAnyRole("USER", "ADMIN") // не будет работать пока не прописать роли в PersonDetails
//                .and()
//                .formLogin().loginPage("/auth/login")
//                .loginProcessingUrl("/process_login")
//                .defaultSuccessUrl("/", true)
//                .failureUrl("/auth/login")
//                .and()
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}