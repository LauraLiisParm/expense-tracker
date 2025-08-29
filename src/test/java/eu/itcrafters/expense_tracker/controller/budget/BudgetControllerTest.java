package eu.itcrafters.expense_tracker.controller.budget;

import eu.itcrafters.expense_tracker.persistence.budget.BudgetEntity;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BudgetRepository budgetRepository;

    @Test
    public void givenValidUserInputNewBudgetGetsSavedIntoDatabase() throws Exception {
        String userInput = """
                {
                    "amount": 100,
                    "name": "Cat",
                    "description": "Cat food"
                }
                """;

        var request = MockMvcRequestBuilders.post("/api/v1/budget")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userInput);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        Optional<BudgetEntity> userAddedBudget = budgetRepository.findByBudgetName("Cat");
        Assertions.assertFalse(userAddedBudget.isEmpty());
        BigDecimal userAddedBudgetAmount = userAddedBudget.get().getBudgetAmount();
        Assertions.assertEquals(BigDecimal.valueOf(100), userAddedBudgetAmount);
    }

    @Test
    public void givenValidUserInputNewBudgetGetsAddedToTheExistingBudget() throws Exception {

    }

}