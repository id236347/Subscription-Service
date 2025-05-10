package subscriptions.subscriptionService.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Модель подписки.
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscriptions")
@Schema(description = "Модель подписки.")
public class Subscription {

    /**
     * Уникальный идентификатор подписки.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор подписки.")
    private int id;

    /**
     * Название подписки.
     */
    @ManyToOne
    @JoinColumn(name = "sub_type_id", referencedColumnName = "id")
    @Schema(description = "Тип подписки.")
    private SubType type;

    /**
     * Подписки пользователя.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @Schema(description = "Подписки пользователя.")
    private User owner;

    @Override
    public String toString() {
        return "name = " + this.type.getName();
    }
}
