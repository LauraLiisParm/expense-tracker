package eu.itcrafters.expense_tracker.persistence.budget;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<BudgetEntity, Integer> {

    @Query("""
        select (count(c) > 0) from budget c
            where c.budgetAmount = :amount and c.category.name = :name
        """)
    boolean budgetExistsBy(Integer amount, String name);

    Optional<BudgetEntity> findByCategoryNameAndBudgetAmount(String categoryName, Integer budgetAmount);
}
