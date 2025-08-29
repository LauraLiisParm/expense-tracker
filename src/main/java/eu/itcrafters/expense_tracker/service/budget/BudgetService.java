package eu.itcrafters.expense_tracker.service.budget;

import eu.itcrafters.expense_tracker.model.budget.Budget;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetEntity;
import eu.itcrafters.expense_tracker.mapper.budget.BudgetMapper;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;


    public void addBudget(Budget budget) {
        LocalDate currentDate = LocalDate.now();
        budget.setBudgetYear((short) currentDate.getYear());
        budget.setBudgetMonth(currentDate);


        BudgetEntity entity = budgetMapper.toEntity(budget);
        budgetRepository.save(entity);
    }
}


