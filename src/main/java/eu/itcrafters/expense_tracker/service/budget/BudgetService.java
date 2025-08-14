package eu.itcrafters.expense_tracker.service.budget;

import eu.itcrafters.expense_tracker.infrastructure.rest.exception.DataNotFoundException;
import eu.itcrafters.expense_tracker.persistence.budget.Budget;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetRepository;
import eu.itcrafters.expense_tracker.service.budget.dto.BudgetDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static eu.itcrafters.myproject.infrastructure.rest.error.Error.BUDGET_EXISTS;


@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public void addBudget(BudgetDto budgetDto) {
        ensureNewBudget(budgetDto);
        Budget budget = createBudget(budgetDto);
        budgetRepository.save(budget);
    }

    private void ensureNewBudget(BudgetDto budgetDto) {
        if (budgetRepository.budgetExistsBy(budgetDto.getAmount(), budgetDto.getName())) {
            throw new DataNotFoundException(BUDGET_EXISTS.getMessage());
        }
    }

    private Budget createBudget(BudgetDto budgetDto) {
        Budget budget = budgetMapper.toBudget(budgetDto);
        addAmountToBudget(budgetDto.getAmount(), budget);
        addNameToBudget(carDto.getFuelTypeId(), budget);
        return budget;
    }
}
