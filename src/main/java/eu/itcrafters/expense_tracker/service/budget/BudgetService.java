package eu.itcrafters.expense_tracker.service.budget;

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

import java.time.LocalDate;
import java.util.List;

import eu.itcrafters.expense_tracker.infrastructure.rest.error.Error;


@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;
    private final CategoryRepository categoryRepository;

    public void addBudget(@Valid BudgetDto budgetDto) {
        validateBudget(budgetDto);
        ensureNewBudget(budgetDto);

        BudgetEntity entity = budgetMapper.toBudgetEntity(budgetDto);

        // Fix: ensure category exists before saving
        Category category = categoryRepository.findByName(budgetDto.getName())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(budgetDto.getName().trim());
                    newCategory.setDescription(budgetDto.getDescription());
                    return categoryRepository.save(newCategory);
                });

        entity.setCategory(category);

        entity.setBudgetMonth(LocalDate.now().withDayOfMonth(1));
        entity.setBudgetYear((short) LocalDate.now().getYear());

        budgetRepository.save(entity);
    }

    private void ensureNewBudget(BudgetDto budgetDto) {
        if (budgetRepository.budgetExistsBy(
                budgetDto.getAmount(),
                budgetDto.getName())) {
            throw new DataNotFoundException("Budget with this amount and category already exists");
        }
    }

    private void validateBudget(BudgetDto budgetDto) {
        if (budgetDto.getAmount() == null || budgetDto.getAmount() < 0) {
            throw new IllegalArgumentException("Amount must be a non-negative integer");
        }
        if (budgetDto.getName() == null || budgetDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
    }

    public BudgetDto findBudget(Integer budgetId) {
        Budget budget = getValidBudget(budgetId);
        return budgetMapper.toBudgetEntity(budget);
    }

    private Budget getValidBudget(Integer budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new DataNotFoundException(Error.BUDGET_NOT_FOUND.getMessage());
    }

    public List<BudgetInfo> findAllBudgets() {
        List<BudgetEntity> budgets = budgetRepository.findAll();
        return budgetMapper.toBudgetInfos(budgets);
    }

    public void updateBudget(Integer budgetId, @Valid BudgetDto budgetDto) {
        Budget budget= createUpdatedBudgetFrom(budgetId, budgetDto);
        budgetRepository.save(budget);
    }

    private Budget createUpdatedBudgetFrom(Integer budgetId, @Valid BudgetDto budgetDto) {
    }

    public void deleteBudget(Integer budgetId) {
        Budget budget = getValidBudget(budgetId);
        deleteAssociatedBudget(budget);
        budgetRepository.delete(budget);
    }

    private void deleteAssociatedBudget(Budget budget) {
    }
}


