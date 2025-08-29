package eu.itcrafters.expense_tracker.controller.budget.dto;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class BudgetInfo extends BudgetEntity implements Serializable {
    private Integer budgetId;
}
