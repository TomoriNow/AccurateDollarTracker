package attnftasap.adt.service;

import attnftasap.adt.model.Expense;
import attnftasap.adt.model.SpendingReport;
import attnftasap.adt.model.Student;
import attnftasap.adt.model.Category;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;

@Service
public interface ExpenseService {
    SpendingReport getSpendingReport(Student student, Month month, int year);
    Expense saveExpense(Expense expense);
    List<Expense> findExpensesByCategoryAndMonth(Category category, Month month);
    List<Expense> findExpensesByStudentAndMonthYear(Student student, Month month, int year);

}
