package eu.itcrafters.expense_tracker.persistence.budget;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Budget {

    private Integer amount;

    private String category;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
