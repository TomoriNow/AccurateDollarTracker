package attnftasap.adt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpendingReportTest {
    public Month month;
    public int year;
    public String categoryNameBase;
    public String categoryDescriptionBase;
    public Student student;
    public List<Category> categoryList;
    public List<Budget> budgetList;
    public List<Expense> expenseList;
    public SpendingReport spendingReport;

    @BeforeEach
    public void setUp() {
        this.month = Month.FEBRUARY;
        this.year = 2024;
        this.student = new Student("username", "email", "password");
        this.categoryNameBase = "categoryName";
        this.categoryDescriptionBase = "categoryDescription";
        this.categoryList = new ArrayList<>();
        this.budgetList = new ArrayList<>();
        this.expenseList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i <= 3; i++) {
            Category category = new Category(student, categoryNameBase + i, categoryDescriptionBase + i);
            categoryList.add(category);
            student.addCategory(category);

            Budget budget = new Budget(200*i, month, year, category, student);
            budgetList.add(budget);

            calendar.set(2024, Calendar.FEBRUARY, i+1);
            Expense expense = new Expense("expenseOne"+i,50*i, calendar.getTime(), category, student);
            calendar.set(2024, Calendar.FEBRUARY, i+1);
            Expense expense1 = new Expense("expenseTwo"+i,100*i, calendar.getTime(), category, student);
            expenseList.add(expense);
            expenseList.add(expense1);
        }


        this.spendingReport = new SpendingReport(student, year, month, budgetList, expenseList);
    }

    @Test
    public void testStudentGet() {
        assertEquals(student, spendingReport.getStudent());
        assertEquals("username", spendingReport.getStudent().getUsername());
        assertEquals("email", spendingReport.getStudent().getEmail());
        assertEquals("password", spendingReport.getStudent().getPassword());
        for (int i = 0; i< categoryList.size(); i++) {
            assertEquals(categoryList.get(i), spendingReport.getStudent().getCategories().get(i));
        }
    }

    @Test
    public void testYearGet() {
        assertEquals(2024, spendingReport.getYear());
    }

    @Test
    public void testMonthGet() {
        assertEquals("FEBRUARY", spendingReport.getMonth().toString());
    }

    @Test
    public void testBudgetListGet() {
        for(int i = 0; i< budgetList.size(); i++) {
            assertEquals(budgetList.get(i), spendingReport.getBudgetList().get(i));
        }
    }

    @Test
    public void testExpenseListGet() {
        for (int i = 0; i < expenseList.size(); i++) {
            assertEquals(expenseList.get(i), spendingReport.getExpenseList().get(i));
        }
    }

    @Test
    public void testTotalSpending() {
        assertEquals(900, spendingReport.getTotalSpending());
    }

    @Test
    public void testTotalExpectedSpending() {
        assertEquals(1200, spendingReport.getTotalExpectedSpending());
    }

    @Test
    public void testTotalSpendingPerCategory() {
        assertEquals(0, spendingReport.getTotalSpendingPerCategory().get(categoryList.getFirst()));
        assertEquals(150, spendingReport.getTotalSpendingPerCategory().get(categoryList.get(1)));
        assertEquals(300, spendingReport.getTotalSpendingPerCategory().get(categoryList.get(2)));
        assertEquals(450, spendingReport.getTotalSpendingPerCategory().get(categoryList.get(3)));
    }

    @Test
    public void testAddingExpense() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024,Calendar.FEBRUARY,28);
        int originalTotalSpending = spendingReport.getTotalSpending();
        Expense expense = new Expense("expense", 780, calendar.getTime(), categoryList.getFirst(), student);
        Expense expense1 = new Expense("expense", 250, calendar.getTime(), categoryList.get(1), student);
        Expense expense2 = new Expense("expense", 970, calendar.getTime(), categoryList.get(2), student);
        Expense expense3 = new Expense("expense", 340, calendar.getTime(), categoryList.get(3), student);
        spendingReport.addExpense(expense);
        spendingReport.addExpense(expense1);
        spendingReport.addExpense(expense2);
        spendingReport.addExpense(expense3);

        assertTrue(spendingReport.getExpenseList().contains(expense));
        assertTrue(spendingReport.getExpenseList().contains(expense1));
        assertTrue(spendingReport.getExpenseList().contains(expense2));
        assertTrue(spendingReport.getExpenseList().contains(expense3));

        assertEquals(originalTotalSpending+780+250+970+340, spendingReport.getTotalSpending());

        assertEquals(780, spendingReport.getTotalSpendingPerCategory().get(categoryList.getFirst()));
        assertEquals(400, spendingReport.getTotalSpendingPerCategory().get(categoryList.get(1)));
        assertEquals(1270, spendingReport.getTotalSpendingPerCategory().get(categoryList.get(2)));
        assertEquals(790, spendingReport.getTotalSpendingPerCategory().get(categoryList.get(3)));
    }
}
