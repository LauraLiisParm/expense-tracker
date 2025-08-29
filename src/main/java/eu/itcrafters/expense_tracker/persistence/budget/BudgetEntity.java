package eu.itcrafters.expense_tracker.persistence.budget;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "budgets")
public class BudgetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "BUDGET_MONTH", nullable = false)
    private LocalDate budgetMonth;

    @NotNull
    @Column(name = "BUDGET_YEAR", nullable = false)
    private Short budgetYear;

    @Column(name = "name")
    private String budgetName;

    @NotNull
    @Column(name = "BUDGET_AMOUNT", nullable = false, precision = 10, scale = 2)
    private BigDecimal budgetAmount;

    @Column(name = "description")
    private String description;

    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt = Instant.now();

}