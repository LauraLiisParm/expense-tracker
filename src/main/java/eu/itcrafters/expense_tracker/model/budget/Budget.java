package eu.itcrafters.expense_tracker.model.budget;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class Budget {

    private Integer id;
    private LocalDate budgetMonth;
    private Short budgetYear;
    private String budgetName;
    private Integer budgetAmount;
    private String description;
}
