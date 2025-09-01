package eu.itcrafters.expense_tracker.infrastructure.rest.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Deprecated
@AllArgsConstructor
@Getter
enum Error { // Deprecated: use ErrorCode instead
    BUDGET_NOT_FOUND("Budget does not exist"),
    BUDGET_EXISTS("Budget already exists");

    private final String message;
}
