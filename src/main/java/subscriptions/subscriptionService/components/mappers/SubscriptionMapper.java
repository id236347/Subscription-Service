package subscriptions.subscriptionService.components.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import subscriptions.subscriptionService.dto.SubTypeDto;
import subscriptions.subscriptionService.dto.SubscriptionDto;
import subscriptions.subscriptionService.exceptions.models.SubTypeNotFoundException;
import subscriptions.subscriptionService.exceptions.models.SubscriptionNotFoundException;
import subscriptions.subscriptionService.exceptions.models.UserNotFoundException;
import subscriptions.subscriptionService.models.SubType;
import subscriptions.subscriptionService.models.Subscription;
import subscriptions.subscriptionService.repositories.SubTypeRepository;
import subscriptions.subscriptionService.repositories.SubscriptionRepository;
import subscriptions.subscriptionService.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class SubscriptionMapper {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubTypeRepository subTypeRepository;

    @Autowired
    public SubscriptionMapper(UserRepository userRepository, SubscriptionRepository subscriptionRepository, SubTypeRepository subTypeRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subTypeRepository = subTypeRepository;
    }


    public SubscriptionDto convertToSubscriptionDto(Subscription subscription) {
        return SubscriptionDto
                .builder()
                .name(subscription.getType().getName())
                .ownerId(subscription.getOwner().getId())
                .build();
    }

    public Subscription convertToSubscription(SubscriptionDto subscriptionDto) {
        return Subscription
                .builder()
                .type(
                        subTypeRepository.findByName(subscriptionDto.getName())
                                .orElseThrow(() -> new SubTypeNotFoundException("Тип подписки с именем " + subscriptionDto.getName() + " не найден!")
                                )
                )
                .owner(
                        userRepository
                                .findById(subscriptionDto.getOwnerId())
                                .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + subscriptionDto.getOwnerId() + " не найден!"))
                )
                .build();
    }

    public List<SubscriptionDto> convertToSubscriptionsDto(List<Subscription> subscriptions) {
        return subscriptions.stream().map(this::convertToSubscriptionDto).collect(Collectors.toList());
    }

    public SubTypeDto convertToSubTypeDto(SubType subType) {
        return SubTypeDto
                .builder()
                .name(subType.getName())
                .subscriptionsIds(subType.getCopies().stream().map(Subscription::getId).collect(Collectors.toList()))
                .build();
    }

    public SubType convertToSubType(SubTypeDto subTypeDto) {
        return SubType
                .builder()
                .name(subTypeDto.getName())
                .copies(
                        subTypeDto.getSubscriptionsIds()
                                .stream()
                                .map(e -> subscriptionRepository
                                        .findById(e)
                                        .orElseThrow(
                                                () -> new SubscriptionNotFoundException("Подписка с id = " + e + " не найдена!")
                                        )
                                )
                                .toList()
                )
                .build();
    }

    public List<SubTypeDto> convertToSubTypesDto(List<SubType> subTypes) {
        return subTypes.stream().map(this::convertToSubTypeDto).collect(Collectors.toList());
    }
}
