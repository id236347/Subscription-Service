package subscriptions.subscriptionService.components.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthProviderImpl(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("Authenticating {}", authentication);
        String login = authentication.getName();

        UserDetails userDetails = null;

        try {
            userDetails = userDetailsService.loadUserByUsername(login);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(login);
        }

        String password = authentication.getCredentials().toString();
        log.info("Password: {}", password);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            log.warn("Password does not match!");
            log.warn("Password: {}", passwordEncoder.encode(password));
            log.warn("Password from db: {}", userDetails.getUsername());
            throw new BadCredentialsException("Некорректный пароль!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
