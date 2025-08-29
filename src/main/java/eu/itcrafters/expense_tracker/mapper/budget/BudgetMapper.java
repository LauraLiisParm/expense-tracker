package eu.itcrafters.expense_tracker.mapper.budget;


import eu.itcrafters.expense_tracker.controller.budget.dto.BudgetDto;
import eu.itcrafters.expense_tracker.model.budget.Budget;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BudgetMapper {

    @Mapping(target = "budgetAmount", source = "amount")
    @Mapping(target = "budgetName", source = "name")
    Budget toBudget(BudgetDto dto);

    BudgetEntity toEntity(Budget model);

}
