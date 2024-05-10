package attnftasap.adt.service;

import attnftasap.adt.model.*;
import attnftasap.adt.repository.BudgetRepository;
import attnftasap.adt.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SummaryServiceTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private SummaryServiceImpl summaryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerateSpendingReport() {
        UUID studentId = UUID.randomUUID();
        Student student = new Student();
        student.setUserUUID(studentId);
        Month month = Month.FEBRUARY;
        int year = 2024;

        List<Budget> budgetList = new ArrayList<>();
        List<Expense> expenseList = new ArrayList<>();

        doReturn(budgetList).when(budgetRepository).findAllByStudentAndMonthAndYear(student, month, year);
        doReturn(expenseList).when(expenseRepository).findExpensesByStudentAndMonthYear(student, month.getValue(), year);

        SpendingReport spendingReport = summaryService.getSummary(student, month, year);

        verify(budgetRepository, times(1)).findAllByStudentAndMonthAndYear(student, month, year);
        verify(expenseRepository, times(1)).findExpensesByStudentAndMonthYear(student, month.getValue(), year);

        assertEquals(student, spendingReport.getStudent());
        assertEquals(year, spendingReport.getYear());
        assertEquals(month, spendingReport.getMonth());
        assertEquals(budgetList, spendingReport.getBudgetList());
        assertEquals(expenseList, spendingReport.getExpenseList());
    }
}
