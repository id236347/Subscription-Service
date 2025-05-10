package subscriptions.subscriptionService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Dto модель подписки.
 */
@Getter
@Setter
@Builder
@Schema(description = "Dto модель подписки.")
public class SubscriptionDto {

    /**
     * Название типа подсписки.
     */
    @Schema(description = "Название типа подсписки.")
    private String name;

    /**
     * Уникальный идентификатор владельца подписки.
     */
    @Schema(description = "Уникальный идентификатор владельца подписки.")
    private int ownerId;

}
