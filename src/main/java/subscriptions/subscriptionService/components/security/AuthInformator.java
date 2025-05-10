package subscriptions.subscriptionService.components.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import subscriptions.subscriptionService.exceptions.auth.UserNotAuthException;
import subscriptions.subscriptionService.models.Role;
import subscriptions.subscriptionService.models.User;
import subscriptions.subscriptionService.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class AuthInformator {

    private final RoleRepository roleRepository;

    @Autowired
    public AuthInformator(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Метод получения аутентифицированного пользователя.
     *
     * @return {@link User}
     * @see User
     * @see Authentication
     */
    public Optional<User> getAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверяем аутентифицирован ли пользователь.
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                return Optional.of((User) principal);
            }
        }

        // Если пользователь не аутентифицирован или principal не User
        return Optional.empty();
    }

    public Integer getAuthenticatedUserId() {
        return getAuthenticatedUser().orElseThrow(() -> new UserNotAuthException("Пользователь не авторизирован!")).getId();
    }

    public void checkAllowedId(int userId) {
        User supposedAuthUser = getAuthenticatedUser()
                .orElseThrow(() -> new UserNotAuthException("Пользователь не авторизирован!"));

        boolean userIsAdmin = isAdmin(supposedAuthUser);


        if (supposedAuthUser.getId() != userId && !userIsAdmin)
            throw new UserNotAuthException("Пользователь может брать только свои данные! Исключение администратор!");
    }

    public boolean isAdmin(User supposedAuthUser) {
        List<String> roles = supposedAuthUser
                .getRoles()
                .stream()
                .map(Role::getName).toList();
        boolean result = roles.contains("ROLE_ADMIN") || roles.contains("ADMIN");
        log.info("isAdmin? -> {}", result);
        return result;
    }

}
