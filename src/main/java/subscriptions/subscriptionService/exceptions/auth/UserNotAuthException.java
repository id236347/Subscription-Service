package subscriptions.subscriptionService.exceptions.auth;

import subscriptions.subscriptionService.exceptions.core.SubscriptionServiceException;

public class UserNotAuthException extends SubscriptionServiceException {
    public UserNotAuthException(String message) {
        super(message);
    }
}
