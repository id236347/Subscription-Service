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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import subscriptions.subscriptionService.components.mappers.SubscriptionMapper;
import subscriptions.subscriptionService.dto.SubTypeDto;
import subscriptions.subscriptionService.dto.SubscriptionDto;
import subscriptions.subscriptionService.services.SubscriptionService;

import java.util.List;

@RestController
@Tag(name = "Ендпоинты на взаимодействие с пользовательскими подписками.")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService, SubscriptionMapper subscriptionMapper) {
        this.subscriptionService = subscriptionService;
        this.subscriptionMapper = subscriptionMapper;
    }

    @PostMapping("/api/users/subscriptions")
    @Operation(summary = "Добавляет подписку на основе входных данных.")
    public ResponseEntity<HttpStatus> addSubscription
            (
                    @RequestBody
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            required = true,
                            description = "Входные данные добавляемой подписки."
                    )
                    SubscriptionDto subscriptionDto
            ) {
        subscriptionService.addSubscription(subscriptionDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}/subscriptions")
    @Operation(summary = "Возвращает список подписок пользователя по его уникальному идентификатору.")
    public ResponseEntity<List<SubscriptionDto>> getSubscriptions
            (
                    @PathVariable
                    @Parameter(
                            required = true,
                            description = "Уникальный идентификатор пользователя, чьи подписки мы ищем.",
                            example = "6"
                    )
                    int id
            ) {
        return new ResponseEntity<>(subscriptionMapper.convertToSubscriptionsDto(subscriptionService.getSubscriptionsOfUser(id)), HttpStatus.OK);
    }

    @DeleteMapping("/api/users/{id}/subscriptions/{sub_id}")
    @Operation(summary = "Удаляет подписку у пользователя по ее уникальным идентификаторам пользователя и подписки.")
    public ResponseEntity<HttpStatus> deleteSubscription
            (
                    @PathVariable
                    @Parameter(
                            required = true,
                            description = "Уникальный идентификатор пользователя.",
                            example = "6"
                    )
                    int id,
                    @PathVariable
                    @Parameter(
                            required = true,
                            description = "Уникальный идентификатор подписки.",
                            example = "6"
                    )
                    int sub_id
            ) {
        subscriptionService.deleteSubscription(sub_id, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/subscriptions/top")
    @Operation(summary = "Возвращает топ 3 типа подписки по популярности.")
    public ResponseEntity<List<SubTypeDto>> getTopSubscriptions() {
        return new ResponseEntity<>(subscriptionService.getTopSubscriptions(), HttpStatus.OK);
    }

}
