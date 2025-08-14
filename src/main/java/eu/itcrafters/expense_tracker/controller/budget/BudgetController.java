package eu.itcrafters.expense_tracker.controller.budget;

import eu.itcrafters.expense_tracker.infrastructure.rest.error.ApiError;
import eu.itcrafters.expense_tracker.service.budget.BudgetService;
import eu.itcrafters.expense_tracker.service.budget.dto.BudgetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Budget Management", description = "Operations related to budget management")

public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping("/budget")
    @Operation(summary = "Adds a new budget to the system", description = "Accepts a BudgetDto object and saves it to the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Budget added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public void addBudget(@RequestBody @Valid BudgetDto budgetDto) {
        budgetService.addBudget(budgetDto);
    }


}
