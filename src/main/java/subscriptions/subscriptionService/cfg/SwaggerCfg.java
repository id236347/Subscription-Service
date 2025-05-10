package subscriptions.subscriptionService.cfg;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * Конфиг Swagger-а.
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Subscription service API",
                version = "1.0",
                description = "API управления пользователями и их подписками."
        )
)
@Configuration
public class SwaggerCfg {

}