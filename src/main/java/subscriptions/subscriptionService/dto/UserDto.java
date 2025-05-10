package subscriptions.subscriptionService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/**
 * Dto модель пользователя.
 */
@Setter
@Getter
@Builder
@Schema(description = "Dto модель пользователя.")
public class UserDto {

    /**
     * Логин для входа пользователя.
     */
    @Schema(description = "Логин для входа пользователя.")
    private String login;

    /**
     * Пароль для входа пользователя.
     */
    @Schema(description = "Пароль для входа пользователя.")
    private String password;

    /**
     * Уникальные идентификаторы подписок пользователя.
     */
    @Schema(description = "Уникальные идентификаторы подписок пользователя.")
    private List<Integer> subscriptionsIds;

    /**
     * Уникальные идентификаторы ролей пользователя.
     */
    @Schema(description = "Уникальные идентификаторы ролей пользователя.")
    private Set<Integer> rolesIds;

}
