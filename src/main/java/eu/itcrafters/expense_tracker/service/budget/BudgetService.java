package eu.itcrafters.expense_tracker.service.budget;

import eu.itcrafters.expense_tracker.controller.budget.dto.BudgetDto;
import eu.itcrafters.expense_tracker.model.budget.Budget;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetEntity;
import eu.itcrafters.expense_tracker.mapper.budget.BudgetMapper;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;


    public void addBudget(Budget budget) {
        log.info("Adding dates to budgets");
        addDatesToBudget(budget);

        log.info("Looking for existing budget");
        Optional<BudgetEntity> existingBudget = budgetRepository.findByBudgetName(budget.getBudgetName());

        if (existingBudget.isEmpty()) {
            log.info("Did not find an existing budget, saving new budget");
            saveNewBudget(budget);
        } else {
            log.info("Found existing budget, updating budget amount");
            addAmountToExistingBudget(budget, existingBudget);
        }
        log.info("Budget adding completed");
    }


    public Budget findBudget(Integer budgetId) {
        log.info("Looking for budget with id: " + budgetId);
        return budgetRepository.findById(budgetId)
                .map(budgetMapper::toBudget)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found with id: " + budgetId));
    }


    public List<Budget> findAllBudgets() {
        log.info("Looking for all budgets in repository");

        log.info("Looking budget entities");
        List<BudgetEntity> entities = budgetRepository.findAll();


        log.info("Mapping budget entities to models");
        List<Budget> budgets = budgetMapper.toBudgetList(entities);

        log.info("Returning all budgets");
        return budgets;
    }


    private void addAmountToExistingBudget(Budget budget, Optional<BudgetEntity> existingBudget) {
        BudgetEntity existing = existingBudget.get();
        BigDecimal existingBudgetAmount = existing.getBudgetAmount();
        BigDecimal newBudgetAmount = existingBudgetAmount.add(BigDecimal.valueOf(budget.getBudgetAmount()));
        existing.setBudgetAmount(newBudgetAmount);
        budgetRepository.save(existing);
    }

    private void saveNewBudget(Budget budget) {
        BudgetEntity entity = budgetMapper.toEntity(budget);
        budgetRepository.save(entity);
    }

    private static void addDatesToBudget(Budget budget) {
        LocalDate currentDate = LocalDate.now();
        budget.setBudgetYear((short) currentDate.getYear());
        budget.setBudgetMonth(currentDate);
    }


}


