package eu.itcrafters.expense_tracker.service.budget;

import eu.itcrafters.expense_tracker.controller.budget.BudgetController;
import eu.itcrafters.expense_tracker.controller.budget.dto.BudgetInfo;
import eu.itcrafters.expense_tracker.infrastructure.rest.exception.DataNotFoundException;
import eu.itcrafters.expense_tracker.persistence.budget.Budget;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetEntity;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetMapper;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetRepository;
import eu.itcrafters.expense_tracker.persistence.category.Category;
import eu.itcrafters.expense_tracker.persistence.category.CategoryRepository;
import eu.itcrafters.expense_tracker.controller.budget.dto.BudgetDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import eu.itcrafters.expense_tracker.infrastructure.rest.error.Error;


@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;
    private final CategoryRepository categoryRepository;

    public void addBudget(Budget budget) {
        BudgetEntity budgetEntity = budgetMapper.toBudgetEntity(budget);

        // check if a budget already exists for this category and amount
        boolean exists = budgetRepository.budgetExistsBy(
                budgetEntity.getBudgetAmount(),
                budgetEntity.getCategory().getName()
        );

        if (exists) {
            // fetch the existing budget entity
            BudgetEntity existingBudget = budgetRepository
                    .findByCategoryNameAndBudgetAmount(
                            budgetEntity.getCategory().getName(),
                            budgetEntity.getBudgetAmount()
                    )
                    .orElseThrow(() -> new IllegalStateException(
                            "Budget should exist but was not found"
                    ));

            // sum using plain int
            int newAmount = existingBudget.getBudgetAmount() + budgetEntity.getBudgetAmount();
            existingBudget.setBudgetAmount(newAmount);

            budgetRepository.save(existingBudget);
        } else {
            // save as a new budget
            budgetRepository.save(budgetEntity);
        }
    }


/*
    public Budget findBudget(Integer budgetId) {
        Budget budget = getValidBudget(budgetId);
        return budgetMapper.toBudgetEntity(budget);
    }


    public List<BudgetInfo> findAllBudgets() {
        List<BudgetEntity> budgets = budgetRepository.findAll();
        return budgetMapper.toBudgetInfos(budgets);
    }


    public void updateBudget(Integer budgetId, @Valid BudgetDto budgetDto) {
        Budget budget= createUpdatedBudgetFrom(budgetId, budgetDto);
        budgetRepository.save(budget);
    }


    public void deleteBudget(Integer budgetId) {
        Budget budget = getValidBudget(budgetId);
        deleteAssociatedBudget(budget);
        budgetRepository.delete(budgetEntity);
    }

 */

}


