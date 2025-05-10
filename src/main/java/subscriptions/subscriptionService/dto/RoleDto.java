package subscriptions.subscriptionService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Dto модель роли пользователя.
 */
@Getter
@Setter
@Builder
@Schema(description = "Dto модель роли пользователя.")
public class RoleDto {

    /**
     * Название роли.
     */
    @Schema(description = "Название роли.")
    private String name;

    /**
     * Уникальные идентификаторы владельцев роли.
     */
    @Schema(description = "Уникальные идентификаторы владельцев роли.")
    private Set<Integer> ownersIds;

}
