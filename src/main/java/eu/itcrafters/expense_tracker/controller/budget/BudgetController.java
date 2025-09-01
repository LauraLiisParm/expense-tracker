package eu.itcrafters.expense_tracker.controller.budget;

import eu.itcrafters.expense_tracker.controller.budget.dto.BudgetInfo;
import eu.itcrafters.expense_tracker.infrastructure.rest.error.ApiError;
import eu.itcrafters.expense_tracker.model.budget.Budget;
import eu.itcrafters.expense_tracker.mapper.budget.BudgetMapper;
import eu.itcrafters.expense_tracker.service.budget.BudgetService;
import eu.itcrafters.expense_tracker.controller.budget.dto.BudgetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Budget Management", description = "Operations related to budget management")

public class BudgetController {

    private final BudgetService budgetService;
    private final BudgetMapper budgetMapper;

    @PostMapping("/budget")
    @Operation(summary = "Adds a new budget to the system", description = "Accepts a BudgetDto object and saves it to the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Budget added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public void addBudget(@RequestBody @Valid BudgetDto budgetDto) {
        log.info("Received budgetAdd request: {}", budgetDto);
        Budget budget = budgetMapper.toBudget(budgetDto);
        budgetService.addBudget(budget);
    }


    @GetMapping("/budget/{budgetId}")
    @Operation(summary = "Finds a budget by its ID", description = "Finds a budget by its ID, if no match is found then error is thrown")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Budget does not exist",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    public Budget findBudget(@PathVariable Integer budgetId) {
        log.info("Received findBudget request: {}", budgetId);
        return budgetService.findBudget(budgetId);
    }

    @GetMapping("/budgets")
    @Operation(summary = "Finds all budgets")
    public List<BudgetInfo> findAllBudgets() {
        log.info("Received findAllBudgets request");
        List<Budget> budgets = budgetService.findAllBudgets();
        return budgetMapper.toBudgetInfoList(budgets);
    }



    @PutMapping("/budget/{budgetId}")
    @Operation(summary = "Updates a budget", description = "If there are any null value fields, those won't get updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid request body: payload validation failed",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Budget does not exit / BudgetType not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public void updateBudget(@PathVariable Integer budgetId, @RequestBody @Valid BudgetDto budgetDto) {
        Budget budget = budgetMapper.toBudget(budgetDto);
        budgetService.updateBudget(budgetId, budget);
    }



    @DeleteMapping("/budget/{budgetId}")
    @Operation(summary = "Deletes a budget by its ID",
            description = "Also checks if any record exist with this budget. If yes, record is deleted")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Budget does not exist",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    public void deleteBudget(@PathVariable Integer budgetId) {
        budgetService.deleteBudget(budgetId);
    }


}
