package eu.itcrafters.expense_tracker.infrastructure.rest.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Error {
    BUDGET_NOT_FOUND("Budget does not exist"),
    BUDGET_EXISTS("Budget already exists");

    private final String message;
}
