package attnftasap.adt.service;

import attnftasap.adt.model.Expense;
import attnftasap.adt.model.SpendingReport;
import attnftasap.adt.model.Student;
import org.springframework.stereotype.Service;

import java.time.Month;

@Service
public interface ExpenseService {
    SpendingReport getSpendingReport(Student student, Month month, int year);
    Expense saveExpense(Expense expense);
}
