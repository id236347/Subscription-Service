package subscriptions.subscriptionService.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import subscriptions.subscriptionService.components.mappers.SubscriptionMapper;
import subscriptions.subscriptionService.components.security.AuthInformator;
import subscriptions.subscriptionService.dto.SubTypeDto;
import subscriptions.subscriptionService.dto.SubscriptionDto;
import subscriptions.subscriptionService.exceptions.models.SubscriptionNotFoundException;
import subscriptions.subscriptionService.exceptions.models.UserNotFoundException;
import subscriptions.subscriptionService.models.Subscription;
import subscriptions.subscriptionService.models.User;
import subscriptions.subscriptionService.repositories.SubscriptionRepository;
import subscriptions.subscriptionService.repositories.UserRepository;
import subscriptions.subscriptionService.services.SubscriptionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис подписок.
 */
@Slf4j
@Service
@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final AuthInformator authInformator;

    @Autowired
    public SubscriptionServiceImpl(UserRepository userRepository, SubscriptionRepository subscriptionRepository, SubscriptionMapper subscriptionMapper, AuthInformator authInformator) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
        this.authInformator = authInformator;
    }


    /**
     * Метод добавляющий новую подписку пользователю.
     *
     * @param subscriptionDto подписка.
     * @see SubscriptionDto
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addSubscription(SubscriptionDto subscriptionDto) {

        int ownerId = subscriptionDto.getOwnerId();

        authInformator.checkAllowedId(ownerId);

        User supposedUser = userRepository.findById(ownerId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id = " + ownerId + " не найден!"));
        log.info("User successfully founded by id = {}", ownerId);

        if (supposedUser.getSubscriptions() == null) {
            log.warn("User {} has no subscriptions", ownerId);
            supposedUser.setSubscriptions(new ArrayList<>());
        }

        supposedUser.getSubscriptions().add(subscriptionMapper.convertToSubscription(subscriptionDto));
        supposedUser.setSubscriptions(supposedUser.getSubscriptions());

        userRepository.save(supposedUser);
        log.info("User successfully added subscriptions!");
    }

    /**
     * Метод возвращающий все подписки пользователя по его уникальному идентификатору.
     *
     * @param userId уникальный идентификатор пользователя.
     * @return подписки пользователя.
     * @see Subscription
     */
    @Override
    public List<Subscription> getSubscriptionsOfUser(int userId) {

        authInformator.checkAllowedId(userId);

        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id = " + userId + " не найден!"))
                .getSubscriptions();
    }

    /**
     * Метод удаляющий подписку пользователя.
     *
     * @param subscriptionId уникальный идентификатор подписки.
     * @param userId         уникальный идентификатор пользователя.
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteSubscription(int subscriptionId, int userId) {

        authInformator.checkAllowedId(userId);

        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь с id = " + userId + " не найден!"));
        subscriptionRepository
                .findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionNotFoundException("Подписка с id = " + subscriptionId + " не найдена!"));
        subscriptionRepository.deleteById(subscriptionId);
        log.info("Subscription successfully deleted!");
    }

    /**
     * Метод возвращающий топ 3 популярных подписок.
     *
     * @return 3 самые популярные подписки.
     */
    @Override
    public List<SubTypeDto> getTopSubscriptions() {
        return subscriptionMapper.convertToSubTypesDto(subscriptionRepository.findTopSubTypes(PageRequest.of(0, 3)));
    }
}
