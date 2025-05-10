package subscriptions.subscriptionService.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import subscriptions.subscriptionService.components.mappers.UserMapper;
import subscriptions.subscriptionService.components.security.AuthInformator;
import subscriptions.subscriptionService.dto.RegistrationDto;
import subscriptions.subscriptionService.dto.UserDto;
import subscriptions.subscriptionService.services.UserService;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Ендпоинты на взаимодействие с пользователем.")
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;
    private final AuthInformator authInformator;

    @Autowired
    public UserController(UserService userService, UserMapper mapper, AuthInformator authInformator) {
        this.userService = userService;
        this.mapper = mapper;
        this.authInformator = authInformator;
    }

    @GetMapping
    @Operation(summary = "Возвращает уникальный идентификатор авторизированного пользователя.")
    public ResponseEntity<Integer> getMyId() {
        return new ResponseEntity<>(authInformator.getAuthenticatedUserId(), HttpStatus.OK);
    }

    @PostMapping("/create")
    @Operation(summary = "Создает нового пользователя на основе входных данных. (Для Админа)")
    public ResponseEntity<HttpStatus> create
            (
                    @RequestBody
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            required = true,
                            description = "Входные данные в виде DTO модели."
                    )
                    UserDto userDto
            ) {
        userService.createUser(userDto, false);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрирует нового пользователя на основе входных данных.")
    public ResponseEntity<HttpStatus> register
            (
                    @RequestBody
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            required = true,
                            description = "Входные данные в виде DTO модели."
                    )
                    RegistrationDto userDto
            ) {
        userService.createUser(mapper.convertToUserDto(userDto), true);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Возвращает сущность пользователя по его уникальному идентификатору.")
    public ResponseEntity<UserDto> getUser
            (
                    @PathVariable
                    @Parameter(
                            required = true,
                            description = "Уникальный идентификатор разыскиваемого пользователя.",
                            example = "6"
                    )
                    int id
            ) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновляет пользователя на основе входных данных.")
    public ResponseEntity<HttpStatus> updateUser
            (
                    @PathVariable
                    @Parameter(
                            required = true,
                            description = "Уникальный идентификатор обновляемого пользователя.",
                            example = "6"
                    )
                    int id,
                    @RequestBody
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            required = true,
                            description = "Входные данные для обновляемого пользователя."
                    )
                    UserDto userDto
            ) {
        userService.updateUser(id, userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаляет пользователя по его уникальному идентификатору.")
    public ResponseEntity<HttpStatus> deleteUser
    (
            @PathVariable
            @Parameter(
                    required = true,
                    description = "Уникальный идентификатор удаляемого пользователя.",
                    example = "6"
            )
            int id
    ) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
