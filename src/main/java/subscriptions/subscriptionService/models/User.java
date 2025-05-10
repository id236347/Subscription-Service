package subscriptions.subscriptionService.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Уникальный идентификатор пользователя в БД.
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Schema(description = "Модель пользователя.")
public class User implements UserDetails {

    /**
     * Уникальный идентификатор пользователя в БД.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор пользователя.")
    private int id;

    /**
     * Логин пользователя для входа.
     */
    @Schema(description = "Логин пользователя для входа.")
    private String login;

    /**
     * Пароль пользователя для входа.
     */
    @Schema(description = "Пароль пользователя для входа.")
    private String password;

    /**
     * Подписки пользователя.
     * Я не беру в счет так называемые семейные подписки, поэтому Один ко Многим.
     */
    @OneToMany(
            mappedBy = "owner",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Schema(description = "Подписки пользователя.")
    private List<Subscription> subscriptions;

    /**
     * Роли пользователя.
     */
    @ManyToMany(
            cascade = {CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @Schema(description = "Роли пользователя.")
    @JoinTable(
            name = "users_and_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    /**
     * Returns the username used to authenticate the user. Cannot return
     * <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        return this.login;
    }
}
