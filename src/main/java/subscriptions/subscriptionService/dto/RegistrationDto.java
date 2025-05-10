package subscriptions.subscriptionService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dto модель регистрационного пользователя.")
public class RegistrationDto {

    @Schema(description = "Логин будущего пользователя.")
    private String login;

    @Schema(description = "Пароль будущего пользователя.")
    private String password;

}
