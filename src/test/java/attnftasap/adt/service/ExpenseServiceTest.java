package attnftasap.adt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import attnftasap.adt.model.*;
import attnftasap.adt.repository.BudgetRepository;
import attnftasap.adt.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {
    @Mock
    private ExpenseRepository expenseRepository;
    @Mock
    private BudgetRepository budgetRepository;
    @InjectMocks
    private ExpenseServiceImpl expenseService;
    public Month month;
    public int year;
    public String categoryNameBase;
    public String categoryDescriptionBase;
    public Student student;
    public List<Category> categoryList;
    public List<Budget> budgetList;
    public List<Expense> expenseList;

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
    }

    @Test
    public void testGetSpendingReport() {
        doReturn(budgetList).when(budgetRepository).findAllByStudentAndMonthAndYear(student, month, year);
        doReturn(expenseList).when(expenseRepository).findExpensesByStudentAndMonthYear(student, month.getValue(), year);

        SpendingReport spendingReport = expenseService.getSpendingReport(student, month, year);

        verify(budgetRepository, times(1)).findAllByStudentAndMonthAndYear(student,month,year);
        verify(expenseRepository, times(1)).findExpensesByStudentAndMonthYear(student,month.getValue(),year);

        assertEquals(student, spendingReport.getStudent());
        assertEquals("username", spendingReport.getStudent().getUsername());
        assertEquals("email", spendingReport.getStudent().getEmail());
        assertEquals("password", spendingReport.getStudent().getPassword());
        for (int i = 0; i< categoryList.size(); i++) {
            assertEquals(categoryList.get(i), spendingReport.getStudent().getCategories().get(i));
        }

        assertEquals(2024, spendingReport.getYear());

        assertEquals("FEBRUARY", spendingReport.getMonth().toString());

        for(int i = 0; i< budgetList.size(); i++) {
            assertEquals(budgetList.get(i), spendingReport.getBudgetList().get(i));
        }

        for (int i = 0; i < expenseList.size(); i++) {
            assertEquals(expenseList.get(i), spendingReport.getExpenseList().get(i));
        }

        assertEquals(900, spendingReport.getTotalSpending());

        assertEquals(0, spendingReport.getTotalSpendingPerCategory().get(categoryList.getFirst()));
        assertEquals(150, spendingReport.getTotalSpendingPerCategory().get(categoryList.get(1)));
        assertEquals(300, spendingReport.getTotalSpendingPerCategory().get(categoryList.get(2)));
        assertEquals(450, spendingReport.getTotalSpendingPerCategory().get(categoryList.get(3)));
    }

    @Test
    public void testSaveExpense() {
        doReturn(expenseList.getFirst()).when(expenseRepository).save(expenseList.getFirst());

        Expense expense = expenseService.saveExpense(expenseList.getFirst());

        verify(expenseRepository, times(1)).save(expenseList.getFirst());
        assertEquals(expense, expenseList.getFirst());
    }
}
