package eu.itcrafters.expense_tracker.persistence.budget;


import eu.itcrafters.expense_tracker.controller.budget.dto.BudgetInfo;
import eu.itcrafters.expense_tracker.persistence.category.Category;
import eu.itcrafters.expense_tracker.controller.budget.dto.BudgetDto;
import org.mapstruct.*;

import java.util.List;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BudgetMapper {
    @Mapping(source = "amount", target = "budgetAmount")
    @Mapping(target = "category", ignore = true) // ignore automatic mapping
    @Mapping(target = "id", ignore = true)
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

    @InheritConfiguration(name = "toBudgetEntity")
    @Mapping(source = "category", target = "budgetId")
    BudgetInfo toBudgetInfo(Budget budget);

    List<BudgetInfo> toBudgetInfos(List<Budget> budgets);

    @Mapping(source = "budgetAmount", target = "amount")
    @Mapping(source = "category", target = "category")
    Budget toBudget(BudgetEntity budgetEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "toBudget")
    Budget updateBudget(BudgetEntity budgetEntity, @MappingTarget Budget budget);
}
