package eu.itcrafters.myproject.infrastructure.rest.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Error {
    RESOURCE_NOT_FOUND("Some error message"),
    BUDGET_EXISTS("Budget already exists");

    private final String message;
}
