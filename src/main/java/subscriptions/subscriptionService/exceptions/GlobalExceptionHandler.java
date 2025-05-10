package subscriptions.subscriptionService.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import subscriptions.subscriptionService.exceptions.auth.UserNotAuthException;
import subscriptions.subscriptionService.exceptions.models.RoleNotFoundException;
import subscriptions.subscriptionService.exceptions.models.SubTypeNotFoundException;
import subscriptions.subscriptionService.exceptions.models.SubscriptionNotFoundException;
import subscriptions.subscriptionService.exceptions.models.UserAlreadyExistException;
import subscriptions.subscriptionService.exceptions.models.UserNotFoundException;
import subscriptions.subscriptionService.models.SubscriptionError;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<SubscriptionError> handleUserNotFoundException(UserNotFoundException e) {
        return generateNotFoundResponseEntity(e.getMessage());
    }

    @ExceptionHandler(SubTypeNotFoundException.class)
    public ResponseEntity<SubscriptionError> handleSubTypeNotFoundException(SubTypeNotFoundException e) {
        return generateNotFoundResponseEntity(e.getMessage());
    }

    @ExceptionHandler(UserNotAuthException.class)
    public ResponseEntity<SubscriptionError> handleUserNotAuthException(UserNotAuthException e) {
        return new ResponseEntity<>(SubscriptionError.builder().errorMsg(e.getMessage()).timeOfOccurrence(LocalDateTime.now()).build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<SubscriptionError> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return generateNotFoundResponseEntity(e.getMessage());
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<SubscriptionError> handleSubscriptionNotFoundException(SubscriptionNotFoundException e) {
        return generateNotFoundResponseEntity(e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<SubscriptionError> handleUserAlreadyExistException(UserAlreadyExistException e) {
        return generateNotFoundResponseEntity(e.getMessage());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<SubscriptionError> handleRoleNotFoundException(RoleNotFoundException e) {
        return new ResponseEntity<>(SubscriptionError.builder().errorMsg(e.getMessage()).timeOfOccurrence(LocalDateTime.now()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<SubscriptionError> generateNotFoundResponseEntity(String errorMsg) {
        return new ResponseEntity<>(SubscriptionError.builder().errorMsg(errorMsg).timeOfOccurrence(LocalDateTime.now()).build(), HttpStatus.NOT_FOUND);
    }
}
