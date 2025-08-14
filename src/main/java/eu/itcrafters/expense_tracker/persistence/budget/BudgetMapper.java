package eu.itcrafters.expense_tracker.persistence.budget;


import eu.itcrafters.expense_tracker.persistence.expense.Category;
import eu.itcrafters.expense_tracker.service.budget.dto.BudgetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BudgetMapper {

    @Mapping(source = "name", target = "category")
    @Mapping(source = "amount", target = "budgetAmount")
    BudgetEntity toBudgetEntity(BudgetDto dto);

    // Helper method for mapping String → Category
    default Category map(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        Category category = new Category();
        category.setName(value.trim());
        return category;
    }
}
