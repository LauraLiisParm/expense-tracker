package eu.itcrafters.expense_tracker.mapper.budget;


import eu.itcrafters.expense_tracker.controller.budget.dto.BudgetDto;
import eu.itcrafters.expense_tracker.model.budget.Budget;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetEntity;
import eu.itcrafters.expense_tracker.controller.budget.dto.BudgetInfo;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BudgetMapper {

    @Mapping(target = "budgetAmount", source = "amount")
    @Mapping(target = "budgetName", source = "name")
    Budget toBudget(BudgetDto dto);

    @Mapping(target = "budgetAmount", source = "budgetAmount")
    @Mapping(target = "budgetName", source = "budgetName")
    BudgetEntity toEntity(Budget model);

    @Mapping(target = "budgetAmount", source = "budgetAmount")
    @Mapping(target = "budgetName", source = "budgetName")
    Budget toBudget(BudgetEntity entity);

    @Mapping(target = "amount", source = "budgetAmount")
    @Mapping(target = "name", source = "budgetName")
    BudgetDto toDto(Budget model);

    @InheritConfiguration(name = "toDto")
    @Mapping(source = "id", target = "budgetId")
    BudgetInfo toBudgetInfo(Budget budget);

    List<Budget> toBudgetList(List<BudgetEntity> entities);

    List<BudgetDto> toDtoList(List<Budget> budgets);

    List<BudgetInfo> toBudgetInfoList(List<Budget> budgets);
}
