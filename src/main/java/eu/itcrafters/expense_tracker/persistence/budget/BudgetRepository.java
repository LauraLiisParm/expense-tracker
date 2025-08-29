package eu.itcrafters.expense_tracker.persistence.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface BudgetRepository extends JpaRepository<BudgetEntity, Integer> {

    @Query(value = """
            select * from budgets where name = :budgetName
            """, nativeQuery = true)
    Optional<BudgetEntity> findByBudgetName(String budgetName);


}
