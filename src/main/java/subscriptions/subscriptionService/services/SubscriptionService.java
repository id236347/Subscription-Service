package subscriptions.subscriptionService.services;

import subscriptions.subscriptionService.dto.SubTypeDto;
import subscriptions.subscriptionService.dto.SubscriptionDto;
import subscriptions.subscriptionService.models.Subscription;

import java.util.List;

/**
 * Контракт сервиса подписок.
 */
public interface SubscriptionService {

    /**
     * Метод добавляющий новую подписку пользователю.
     *
     * @param subscriptionDto подписка.
     * @see SubscriptionDto
     */
    void addSubscription(SubscriptionDto subscriptionDto);

    /**
     * Метод возвращающий все подписки пользователя по его уникальному идентификатору.
     *
     * @param userId уникальный идентификатор пользователя.
     * @return подписки пользователя.
     * @see Subscription
     */
    List<Subscription> getSubscriptionsOfUser(int userId);

    /**
     * Метод удаляющий подписку пользователя.
     *
     * @param subscriptionId уникальный идентификатор подписки.
     * @param userId         уникальный идентификатор пользователя.
     */
    void deleteSubscription(int subscriptionId, int userId);

    /**
     * Метод возвращающий топ 3 популярных подписок.
     *
     * @return 3 самые популярные подписки.
     */
    List<SubTypeDto> getTopSubscriptions();

}
