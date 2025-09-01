package eu.itcrafters.expense_tracker.controller.budget;

import eu.itcrafters.expense_tracker.persistence.budget.BudgetEntity;
import eu.itcrafters.expense_tracker.persistence.budget.BudgetRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BudgetRepository budgetRepository;

    @BeforeEach
    void cleanUpCatBudget() {
        budgetRepository.findByBudgetName("Cat").ifPresent(budgetRepository::delete);
    }

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

    @Test
    void getBudgetById_returnsBudget_whenExists() throws Exception {
        // Use an existing budget from data.sql (e.g., "Food")
        BudgetEntity any = budgetRepository.findAll().get(0);
        mockMvc.perform(get("/api/v1/budget/" + any.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.budgetName", is(any.getBudgetName())))
                .andExpect(jsonPath("$.budgetAmount", is(any.getBudgetAmount().intValue())))
                .andExpect(jsonPath("$.description", is(any.getDescription())));
    }

    @Test
    void getAllBudgets_returnsList() throws Exception {
        mockMvc.perform(get("/api/v1/budgets"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", isA(List.class)))
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    void updateBudget_updatesFields() throws Exception {
        BudgetEntity entity = budgetRepository.findAll().get(0);
        String payload = """
                {
                  "amount": 777,
                  "name": "UpdatedName",
                  "description": "Updated description"
                }
                """;
        mockMvc.perform(put("/api/v1/budget/" + entity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().is2xxSuccessful());

        BudgetEntity updated = budgetRepository.findById(entity.getId()).orElseThrow();
        Assertions.assertEquals("UpdatedName", updated.getBudgetName());
        Assertions.assertEquals(BigDecimal.valueOf(777), updated.getBudgetAmount());
        Assertions.assertEquals("Updated description", updated.getDescription());
    }

    @Test
    void deleteBudget_removesEntity() throws Exception {
        // Create a temporary budget to delete
        String payload = """
                {
                  "amount": 50,
                  "name": "Cat",
                  "description": "To delete"
                }
                """;
        performRequest(mockAddBudgetEndpointRequest(payload));
        BudgetEntity toDelete = budgetRepository.findByBudgetName("Cat").orElseThrow();

        mockMvc.perform(delete("/api/v1/budget/" + toDelete.getId()))
                .andExpect(status().is2xxSuccessful());

        Assertions.assertTrue(budgetRepository.findById(toDelete.getId()).isEmpty());
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
}