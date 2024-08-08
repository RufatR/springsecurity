package com.bankrufat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class ProjectSecurityConfigProd {


    @Bean
   public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
//        http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());

        http.csrf(csrfConfig->csrfConfig.disable());
        http.authorizeHttpRequests(
                (requests) ->
                        requests
                                .requestMatchers("/myAccount", "/myBalance", "/myCards", "/myLoans")
                                .authenticated()
                                .requestMatchers("/contact", "/notices", "/error" ,"/register").permitAll());


//        http.formLogin(flc->flc.disable());

        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }


//    @Bean
//    public UserDetailsService getUserDetails(DataSource dataSource) {
//        UserDetails user = User.withUsername("user")
//                .password("{noop}RufatRustam@12345")
//                .authorities("read")
//                .build();
//        UserDetails admin = User.withUsername("admin")
//                .password("{bcrypt}$2a$12$eZU7x0HVbnpN..C39IHhn.b82rEzZKOdp5MfpfE0.4JkFm4jPDqMi")
//                .authorities("admin").build();
//        return new InMemoryUserDetailsManager(user, admin);
    /**
     * commente aldim cunki artiq Jdbcden istifade edib oz db de yaradacam
     */

    /**
     * artiq jdbc ni de serhe aliram cunki oz custom tableden username aid email e yoxluyacam
     */
//        return new JdbcUserDetailsManager(dataSource);
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
   public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
