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
public class Budget {
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

}