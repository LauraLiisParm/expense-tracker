package eu.itcrafters.expense_tracker.infrastructure.rest.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}
