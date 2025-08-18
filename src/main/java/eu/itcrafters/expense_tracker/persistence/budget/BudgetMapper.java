package eu.itcrafters.expense_tracker.persistence.budget;


import eu.itcrafters.expense_tracker.persistence.category.Category;
import eu.itcrafters.expense_tracker.service.budget.dto.BudgetDto;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BudgetMapper {
    @Mapping(source = "amount", target = "budgetAmount")
    @Mapping(target = "category", ignore = true) // ignore automatic mapping
    BudgetEntity toBudgetEntity(BudgetDto dto);

    @AfterMapping
    default void mapCategory(BudgetDto dto, @MappingTarget BudgetEntity entity) {
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            Category category = new Category();
            category.setName(dto.getName().trim());
            category.setDescription(dto.getDescription());
            entity.setCategory(category);
        }
    }
}
