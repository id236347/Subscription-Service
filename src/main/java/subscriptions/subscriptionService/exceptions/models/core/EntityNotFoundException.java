package subscriptions.subscriptionService.exceptions.models.core;

import subscriptions.subscriptionService.exceptions.core.SubscriptionServiceException;

public class EntityNotFoundException extends SubscriptionServiceException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
