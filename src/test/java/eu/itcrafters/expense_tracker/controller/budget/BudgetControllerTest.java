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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
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
    void givenValidUserInputNewBudgetGetsSavedIntoDatabase() throws Exception {
        String userInput = """
                {
                    "amount": 100,
                    "name": "Cat",
                    "description": "Cat food"
                }
                """;

        var request = mockAddBudgetEndpointRequest(userInput);

        performRequest(request);

        Optional<BudgetEntity> userAddedBudget = budgetRepository.findByBudgetName("Cat");
        Assertions.assertFalse(userAddedBudget.isEmpty());
        BigDecimal userAddedBudgetAmount = userAddedBudget.get().getBudgetAmount();
        Assertions.assertEquals(BigDecimal.valueOf(100), userAddedBudgetAmount);
    }

    private void performRequest(MockHttpServletRequestBuilder request) throws Exception {
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    private static MockHttpServletRequestBuilder mockAddBudgetEndpointRequest(String userInput) {
        return MockMvcRequestBuilders.post("/api/v1/budget")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userInput);
    }

    @Test
    void givenValidUserInputNewBudgetGetsAddedToTheExistingBudget() throws Exception {
        String userInput = """
                {
                    "amount": 100,
                    "name": "Cat",
                    "description": "Cat food"
                }
                """;

        String userInput2 = """
                {
                    "amount": 100,
                    "name": "Cat",
                    "description": "Cat food"
                }
                """;

        var request = mockAddBudgetEndpointRequest(userInput);
        performRequest(request);

        Optional<BudgetEntity> userAddedBudget = budgetRepository.findByBudgetName("Cat");
        Assertions.assertFalse(userAddedBudget.isEmpty());
        BigDecimal userAddedBudgetAmount = userAddedBudget.get().getBudgetAmount();
        Assertions.assertEquals(BigDecimal.valueOf(100), userAddedBudgetAmount);

        var request2 = mockAddBudgetEndpointRequest(userInput2);
        performRequest(request2);
        Optional<BudgetEntity> userAddedBudget2 = budgetRepository.findByBudgetName("Cat");
        BigDecimal userAddedBudgetAmount2 = userAddedBudget2.get().getBudgetAmount();
        Assertions.assertEquals(BigDecimal.valueOf(200), userAddedBudgetAmount2);
    }

}