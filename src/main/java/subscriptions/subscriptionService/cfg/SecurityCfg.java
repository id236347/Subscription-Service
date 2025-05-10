package subscriptions.subscriptionService.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityCfg {

    private final AuthenticationProvider authProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public SecurityCfg(AuthenticationProvider authProvider, AuthenticationEntryPoint authenticationEntryPoint) {
        this.authProvider = authProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/api/users/login", "/api/users", "/v3/**", "/swagger-ui/**").permitAll();
                            auth.requestMatchers("/api/users/create").hasRole("ADMIN");
                            auth.anyRequest().authenticated();
                        }
                )
                .authenticationProvider(authProvider)
                .exceptionHandling(e ->
                        e.authenticationEntryPoint(authenticationEntryPoint)
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
