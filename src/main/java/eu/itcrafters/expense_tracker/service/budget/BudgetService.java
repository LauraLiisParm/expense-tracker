package eu.itcrafters.expense_tracker.service.budget;

import eu.itcrafters.expense_tracker.infrastructure.rest.exception.DataNotFoundException;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetEntity;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetMapper;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetRepository;
import eu.itcrafters.expense_tracker.persistence.expense.Category;
import eu.itcrafters.expense_tracker.service.budget.dto.BudgetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static eu.itcrafters.myproject.infrastructure.rest.error.Error.BUDGET_EXISTS;


@Service
@RequiredArgsConstructor
public class BudgetService {

        private final BudgetRepository budgetRepository;
        private final BudgetMapper budgetMapper;

    public void addBudget(BudgetDto budgetDto) {
            ensureNewBudget(budgetDto);
            BudgetEntity budgetEntity = createBudget(budgetDto);
            budgetRepository.save(budgetEntity);
        }

        private void ensureNewBudget(BudgetDto budgetDto) {
            if (budgetRepository.budgetExistsBy(
                    BigDecimal.valueOf(budgetDto.getAmount()),
                    budgetDto.getName())) {
                throw new DataNotFoundException(BUDGET_EXISTS.getMessage());
            }
        }

        private BudgetEntity createBudget(BudgetDto budgetDto) {
            BudgetEntity budgetEntity = budgetMapper.toBudgetEntity(budgetDto);
            addAmountToBudget(BigDecimal.valueOf(budgetDto.getAmount()), budgetEntity);
            addNameToBudget(budgetDto.getName(), budgetEntity);
            return budgetEntity;
        }

        private void addAmountToBudget(BigDecimal amount, BudgetEntity budgetEntity) {
            if (amount != null && amount.compareTo(BigDecimal.ZERO) >= 0) {
                budgetEntity.setBudgetAmount(amount);
            } else {
                throw new IllegalArgumentException("Amount must be non-negative");
            }
        }

        private void addNameToBudget(String categoryName, BudgetEntity budgetEntity) {
            if (categoryName != null && !categoryName.trim().isEmpty()) {
                Category category = budgetEntity.getCategory();
                if (category == null) {
                    category = new Category();
                    budgetEntity.setCategory(category);
                }
                category.setName(categoryName.trim()); // Category must have setName()
            } else {
                throw new IllegalArgumentException("Category name cannot be empty");
            }
        }
    }
