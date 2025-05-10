package subscriptions.subscriptionService.components.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import subscriptions.subscriptionService.models.SubscriptionError;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@Component
public class SubServiceAuthenticationEntryPoint implements AuthenticationEntryPoint {


    private final ObjectMapper objectMapper;

    @Autowired
    public SubServiceAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8).toString());
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        SubscriptionError
                                .builder()
                                .errorMsg("Неправильный логин или пароль!")
                                .timeOfOccurrence(LocalDateTime.now())
                                .build()
                )
        );
    }

}
