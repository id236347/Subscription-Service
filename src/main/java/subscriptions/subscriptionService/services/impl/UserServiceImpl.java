package subscriptions.subscriptionService.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import subscriptions.subscriptionService.components.mappers.UserMapper;
import subscriptions.subscriptionService.components.security.AuthInformator;
import subscriptions.subscriptionService.dto.UserDto;
import subscriptions.subscriptionService.exceptions.auth.UserNotAuthException;
import subscriptions.subscriptionService.exceptions.models.RoleNotFoundException;
import subscriptions.subscriptionService.exceptions.models.UserAlreadyExistException;
import subscriptions.subscriptionService.exceptions.models.UserNotFoundException;
import subscriptions.subscriptionService.models.Role;
import subscriptions.subscriptionService.models.User;
import subscriptions.subscriptionService.repositories.RoleRepository;
import subscriptions.subscriptionService.repositories.UserRepository;
import subscriptions.subscriptionService.services.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Сервис пользователей.
 */
@Slf4j
@Service
@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final AuthInformator authInformator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserMapper userMapper, AuthInformator authInformator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.authInformator = authInformator;
    }

    /**
     * Метод создания пользователя.
     *
     * @param userDto пользователь, который будет сохранен в БД.
     * @see User
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createUser(UserDto userDto, boolean isRegister) {

        log.info("Starting user creation...");

        User user = userMapper.convertToUser(userDto);

        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            log.error("Пользователь с логином {} уже существует!", user.getLogin());
            throw new UserAlreadyExistException("Пользователь с логином = " + user.getLogin() + " уже существует!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        Optional<Role> expectedUserRole = roleRepository.findByName("ROLE_USER");

        Role defaultRole = expectedUserRole.orElseThrow(() -> new RoleNotFoundException("Роли НЕ инициализированы на сервере!"));
        log.info("Default role successfully found!");

        boolean isAdmin = authInformator.isAdmin(authInformator.getAuthenticatedUser()
                .orElseThrow(() -> new UserNotAuthException("Пользователь не аутентифицирован!")));

        log.info("isAdmin ? -> {}", isAdmin);

        if (isAdmin && !isRegister)
            user.setRoles(new HashSet<>(user.getRoles()));
        else
            user.setRoles(new HashSet<>(Set.of(defaultRole)));

        // Сохраняемся.
        userRepository.save(user);
        log.info("User successfully created!");
    }

    /**
     * Метод поиск пользователя по уникальному идентификатору.
     *
     * @param userId уникальный идентификатор пользователя.
     * @return {@link User} пользователь с подаваемым уникальным идентификатором.
     * @see User
     */
    @Override
    public UserDto getUser(int userId) {

        authInformator.checkAllowedId(userId);

        var user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id = " + userId + " не найден!"));
        return userMapper.convertToUserDto(user);
    }

    /**
     * Метод редактирования пользователя.
     *
     * @param userId уникальный идентификатор редактируемого пользователя.
     * @param user   отредактированный пользователь.
     * @see User
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateUser(int userId, UserDto user) {

        authInformator.checkAllowedId(userId);

        Optional<User> supposedUser = userRepository
                .findByLogin(user.getLogin());

        // Проверка на теску
        if (supposedUser.isPresent()) {
            if (supposedUser.get().getId() != userId)
                throw new UserAlreadyExistException("Пользователя с данным именем уже существует и это не вы!");
        }

        User userFromDb = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id = " + userId + " не найден!"));

        log.info("User fromDb: {}", userFromDb);
        log.info("User fromDb subscriptions: {}", userFromDb.getSubscriptions());
        log.info("User successfully founded by id = {}", userId);

        User userToUpdate = userMapper.convertToUser(user);

        // Не назначаем роли, если обращающийся не Админ
        if (!authInformator.isAdmin(userToUpdate)) {
            userToUpdate.setRoles(userFromDb.getRoles());
        }

        userToUpdate.setId(userId);

        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userToUpdate);

        log.info("User successfully updated!");
    }

    /**
     * Метод удаления пользователя.
     *
     * @param userId уникальный идентификатор пользователя.
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteUser(int userId) {

        authInformator.checkAllowedId(userId);

        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь с id = " + userId + " не найден!"));
        userRepository.deleteById(userId);
        log.info("User successfully deleted!");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с логином = " + username + " не найден!"));
    }
}
