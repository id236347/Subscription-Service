package subscriptions.subscriptionService.models;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Модель типа подписки.
 */
@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sub_types")
@Schema(description = "Модель типа подписки.")
public class SubType {

    /**
     * Уникальный идентификатор типа подписки.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор типа подписки.")
    private int id;

    /**
     * Имя типа подписки.
     */
    @Schema(description = "Имя типа подписки.")
    private String name;

    @OneToMany(
            mappedBy = "type",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            orphanRemoval = false // Захотелось явно.
    )
    private List<Subscription> copies;
}
