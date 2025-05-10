package subscriptions.subscriptionService.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Модель ошибки управления пользователями и их подписками.
 */
@Getter
@Setter
@Builder
public class SubscriptionError implements Serializable {

    /**
     * Текст сообщения ошибки.
     */
    private String errorMsg;

    /**
     * Время возникновения ошибки.
     */
    private LocalDateTime timeOfOccurrence;

}
