package attnftasap.adt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetTest {
    private UUID budgetId;
    private int amount;
    private  int year;
    private Month month;
    private Student owner;
    private Category category;
    private Budget budget;

    @BeforeEach
    public void setUp() {
        this.amount = 500000000;
        this.budgetId = UUID.randomUUID();
        this.month = Month.FEBRUARY;
        this.year = 2024;
        this.owner = new Student("username", "email", "password");
        this.category = new Category(owner, "categoryName", "categoryDescription");
        this.budget = new Budget();
    }

    @Test
    public void testConstructor() {
        Budget budget1 = new Budget(amount, month, year, category, owner);

        assertEquals(500000000, budget1.getAmount());
        assertEquals("FEBRUARY", budget1.getMonth().toString());
        assertEquals(2024, budget1.getYear());
        assertEquals(category, budget1.getCategory());
        assertEquals(owner, budget1.getStudent());
    }

    @Test
    public void testGetSetAmount() {
        budget.setAmount(amount);

        assertEquals(500000000, budget.getAmount());
    }

    @Test
    public void testGetSetBudgetUUID() {
        budget.setBudgetId(budgetId);

        assertEquals(budgetId, budget.getBudgetId());
    }

    @Test
    public void testGetSetMonth() {
        budget.setMonth(month);

        assertEquals("FEBRUARY", budget.getMonth().toString());
    }

    @Test
    public void testGetSetYear() {
        budget.setYear(year);

        assertEquals(2024, budget.getYear());
    }

    @Test
    public void testGetSetStudent() {
        budget.setStudent(owner);

        assertEquals(owner, budget.getStudent());
        assertEquals("username", budget.getStudent().getUsername());
        assertEquals("email", budget.getStudent().getEmail());
        assertEquals("password", budget.getStudent().getPassword());
    }

    @Test
    public void testGetSetCategory() {
        budget.setCategory(category);

        assertEquals(category, budget.getCategory());
        assertEquals(owner, budget.getCategory().getStudent());
        assertEquals("categoryName", budget.getCategory().getName());
        assertEquals("categoryDescription", budget.getCategory().getDescription());
    }
}
