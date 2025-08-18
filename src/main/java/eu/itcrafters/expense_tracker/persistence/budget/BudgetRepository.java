package eu.itcrafters.expense_tracker.persistence.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BudgetRepository extends JpaRepository<BudgetEntity, Integer>{

    @Query("select (count(c) > 0) from BudgetEntity c " +
            "where c.budgetAmount = :amount and c.category.name = :name")
    boolean budgetExistsBy(Integer amount, String name);
}
