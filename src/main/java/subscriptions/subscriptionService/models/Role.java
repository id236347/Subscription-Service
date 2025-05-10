package subscriptions.subscriptionService.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@Schema(description = "Модель роли пользователя.")
public class Role implements GrantedAuthority {

    /**
     * Уникальный идентификатор роли.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор роли.")
    private int id;

    /**
     * Название роли.
     */
    @Schema(description = "Название роли.")
    private String name;

    /**
     * Пользователи, кому принадлежит роль.
     */
    @Schema(description = "Пользователи обладающие данной ролью.")
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> owners;

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return "name: " + name;
    }
}