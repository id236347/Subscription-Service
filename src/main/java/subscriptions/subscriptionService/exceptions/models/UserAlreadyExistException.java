package subscriptions.subscriptionService.exceptions.models;

import subscriptions.subscriptionService.exceptions.models.core.EntityAlreadyExistException;

public class UserAlreadyExistException extends EntityAlreadyExistException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
