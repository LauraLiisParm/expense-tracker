package eu.itcrafters.expense_tracker.service.budget.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetDto implements Serializable {

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    @Size(min = 1)
    private String name;
}
