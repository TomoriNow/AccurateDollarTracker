package attnftasap.adt.service;

import attnftasap.adt.model.Budget;
import attnftasap.adt.model.Expense;
import attnftasap.adt.model.SpendingReport;
import attnftasap.adt.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Instant;
import java.time.ZoneId;

@Service
public class SummaryServiceImpl implements SummaryService {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private BudgetService budgetService;

    @Override
    public SpendingReport getSummary(Student student, Month month, int year) {
        List<Budget> budgetList = budgetService.findAllByStudentAndMonthAndYear(student, month, year);
        List<Expense> expenseList = expenseService.findExpensesByStudentAndMonthYear(student, month, year);

        return new SpendingReport(student, year, month, budgetList, expenseList);
    }

    public Map<LocalDate, Integer> calculateDailySpending(SpendingReport spendingReport) {
        Map<LocalDate, Integer> dailySpending = new HashMap<>();

        for (Expense expense : spendingReport.getExpenseList()) {
            Instant instant = expense.getDate().toInstant();
            LocalDate expenseDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            dailySpending.put(expenseDate, dailySpending.getOrDefault(expenseDate, 0) + expense.getCost());
        }

        return dailySpending;
    }

}
