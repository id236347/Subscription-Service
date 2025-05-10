package subscriptions.subscriptionService.exceptions.models.core;

import subscriptions.subscriptionService.exceptions.core.SubscriptionServiceException;

public class EntityAlreadyExistException extends SubscriptionServiceException {
    public EntityAlreadyExistException(String message) {
        super(message);
    }
}
