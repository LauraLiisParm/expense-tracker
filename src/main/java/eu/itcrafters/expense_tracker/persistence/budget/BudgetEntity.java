package eu.itcrafters.expense_tracker.persistence.budget;

import eu.itcrafters.expense_tracker.persistence.expense.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "BUDGET")
public class BudgetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUDGET_ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @NotNull
    @Column(name = "BUDGET_MONTH", nullable = false)
    private LocalDate budgetMonth;

    @NotNull
    @Column(name = "BUDGET_YEAR", nullable = false)
    private Short budgetYear;

    @NotNull
    @Column(name = "BUDGET_AMOUNT", nullable = false, precision = 10, scale = 2)
    private BigDecimal budgetAmount;

    @ColumnDefault("0.00")
    @Column(name = "EXPENSE_AMOUNT", precision = 10, scale = 2)
    private BigDecimal expenseAmount;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATED_AT")
    private Instant createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getBudgetMonth() {
        return budgetMonth;
    }

    public void setBudgetMonth(LocalDate budgetMonth) {
        this.budgetMonth = budgetMonth;
    }

    public Short getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(Short budgetYear) {
        this.budgetYear = budgetYear;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public BigDecimal getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(BigDecimal expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}