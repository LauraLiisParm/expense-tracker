package eu.itcrafters.expense_tracker.persistence.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;


public interface BudgetRepository extends JpaRepository<BudgetEntity, Integer> {

    @Query(value = """
            select * from budgets where name = :budgetName
            """, nativeQuery = true)
    Optional<BudgetEntity> findByBudgetName(String budgetName);

    Optional<BudgetEntity> findById(Integer id);

    @Transactional
    @Modifying
    @Query("update BudgetEntity b set b.id = ?1")
    int updateIdBy(Integer id);

    @Transactional
    @Modifying
    @Query("update BudgetEntity b set b.budgetAmount = ?1, b.budgetName = ?2, b.description = ?3 where b.id = ?4")
    void updateBudgetAmountAndBudgetNameAndDescriptionById(BigDecimal budgetAmount, String budgetName, String description, Integer id);
}
