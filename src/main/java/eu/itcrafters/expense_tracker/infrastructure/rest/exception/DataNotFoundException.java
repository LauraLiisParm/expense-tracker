package eu.itcrafters.expense_tracker.infrastructure.rest.exception;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String message) {
        super(message);
    }
}
