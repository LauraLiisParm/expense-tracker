package eu.itcrafters.expense_tracker.infrastructure.rest.exception;

import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {
    private final String message;

    public DataNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
