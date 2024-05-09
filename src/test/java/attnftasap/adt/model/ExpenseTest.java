package attnftasap.adt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseTest {
    private UUID expenseId;
    private String name;
    private int cost;
    private Date date;
    private Student owner;
    private Category category;
    private Expense expense;

    @BeforeEach
    public void setUp() {
        this.name = "name";
        this.cost = 500;
        this.expenseId = UUID.randomUUID();
        this.date = new Date();
        this.owner = new Student("username", "email", "password");
        this.category = new Category(owner, "categoryName", "categoryDescription");
        this.expense = new Expense();
    }

    @Test
    public void testConstructor() {
        Expense expense1 = new Expense(name, cost, date, category, owner);

        assertEquals("name", expense1.getName());
        assertEquals(500, expense1.getCost());
        assertEquals(date, expense1.getDate());
        assertEquals(category, expense1.getCategory());
        assertEquals(owner, expense1.getStudent());
    }

    @Test
    public void testGetSetName() {
        expense.setName(name);

        assertEquals("name", expense.getName());
    }

    @Test
    public void testGetSetCost() {
        expense.setCost(cost);

        assertEquals(500, expense.getCost());
    }

    @Test
    public void testGetSetExpenseUUID() {
        expense.setExpenseId(expenseId);

        assertEquals(expenseId, expense.getExpenseId());
    }

    @Test
    public void testGetSetDate() {
        expense.setDate(date);

        assertEquals(date, expense.getDate());
    }

    @Test
    public void testGetSetStudent() {
        expense.setStudent(owner);

        assertEquals(owner, expense.getStudent());
        assertEquals("username", expense.getStudent().getUsername());
        assertEquals("email", expense.getStudent().getEmail());
        assertEquals("password", expense.getStudent().getPassword());
    }

    @Test
    public void testGetSetCategory() {
        expense.setCategory(category);

        assertEquals(category, expense.getCategory());
        assertEquals(owner, expense.getCategory().getStudent());
        assertEquals("categoryName", expense.getCategory().getName());
        assertEquals("categoryDescription", expense.getCategory().getDescription());
    }
}
