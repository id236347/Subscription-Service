package subscriptions.subscriptionService.services;

import subscriptions.subscriptionService.dto.UserDto;
import subscriptions.subscriptionService.models.User;

/**
 * Контракт сервиса пользователей.
 */
public interface UserService {


    /**
     * Метод создания пользователя.
     *
     * @param userDto пользователь, который будет сохранен в БД.
     * @see User
     */
    void createUser(UserDto userDto, boolean isRegister);

    /**
     * Метод поиск пользователя по уникальному идентификатору.
     *
     * @param userId уникальный идентификатор пользователя.
     * @return {@link User} пользователь с подаваемым уникальным идентификатором.
     * @see User
     */
    UserDto getUser(int userId);

    /**
     * Метод редактирования пользователя.
     *
     * @param userId уникальный идентификатор редактируемого пользователя.
     * @param user   отредактированный пользователь.
     * @see User
     */
    void updateUser(int userId, UserDto user);

    /**
     * Метод удаления пользователя.
     *
     * @param userId уникальный идентификатор пользователя.
     */
    void deleteUser(int userId);

}
