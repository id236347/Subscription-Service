package subscriptions.subscriptionService.components.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import subscriptions.subscriptionService.dto.RegistrationDto;
import subscriptions.subscriptionService.dto.UserDto;
import subscriptions.subscriptionService.exceptions.models.SubscriptionNotFoundException;
import subscriptions.subscriptionService.exceptions.models.UserNotFoundException;
import subscriptions.subscriptionService.models.Role;
import subscriptions.subscriptionService.models.Subscription;
import subscriptions.subscriptionService.models.User;
import subscriptions.subscriptionService.repositories.RoleRepository;
import subscriptions.subscriptionService.repositories.SubscriptionRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserMapper {

    private final SubscriptionRepository subscriptionRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserMapper(SubscriptionRepository subscriptionRepository, RoleRepository roleRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.roleRepository = roleRepository;
    }

    public User convertToUser(UserDto userDto) {

        log.info("SUBS IDS: {}", userDto.getSubscriptionsIds().toString());

        return User
                .builder()
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .roles(generateRolesByDto(userDto))
                .subscriptions(generateSubscriptionsByDto(userDto))
                .build();
    }

    public UserDto convertToUserDto(User user) {
        return UserDto
                .builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .rolesIds(generateRolesByUser(user))
                .subscriptionsIds(generateSubscriptionsByUser(user))
                .build();
    }

    private Set<Role> generateRolesByDto(UserDto userDto) {

        if (userDto.getRolesIds() == null) {
            log.warn("UserDto {} has no rolesIds!", userDto.getLogin());
            return new HashSet<>();
        }

        return userDto
                .getRolesIds()
                .stream()
                .map(u -> roleRepository
                        .findById(u)
                        .orElseThrow(
                                () -> new UserNotFoundException("Role with id = " + u + " not found!")
                        )
                )
                .collect(Collectors.toSet());
    }

    private Set<Integer> generateRolesByUser(User user) {
        if (user.getRoles() == null) {
            log.error("User with id = {} has no roles!", user.getId());
            return new HashSet<>();
        }
        return user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
    }

    private List<Subscription> generateSubscriptionsByDto(UserDto userDto) {

        if (userDto.getSubscriptionsIds() == null) {
            log.warn("UserDto {} has no subscriptionsIds!", userDto.getLogin());
            return new ArrayList<>();
        }

        var result = userDto
                .getSubscriptionsIds()
                .stream()
                .map(s -> subscriptionRepository
                        .findById(s)
                        .orElseThrow(
                                () -> new SubscriptionNotFoundException("Subscription with id = " + s + " not found!")
                        )
                )
                .toList();

        log.info("subs: {}", result);

        return result;
    }

    public UserDto convertToUserDto(RegistrationDto registrationDto) {
        return UserDto
                .builder()
                .login(registrationDto.getLogin())
                .password(registrationDto.getPassword())
                .build();
    }

    private List<Integer> generateSubscriptionsByUser(User user) {
        if (user.getSubscriptions() == null) {
            log.warn("User {} has no subscriptions", user.getLogin());
            return new ArrayList<>();
        }

        log.info("Subs: {}", user.getSubscriptions());

        List<Integer> result = user.getSubscriptions().stream().map(Subscription::getId).collect(Collectors.toList());
        log.info("result: {}", result);
        return result;
    }

}
