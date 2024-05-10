package attnftasap.adt.service;

import attnftasap.adt.model.Budget;
import attnftasap.adt.model.Expense;
import attnftasap.adt.model.SpendingReport;
import attnftasap.adt.model.Student;
import attnftasap.adt.repository.BudgetRepository;
import attnftasap.adt.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    BudgetRepository budgetRepository;

    @Override
    public SpendingReport getSpendingReport(Student student, Month month, int year) {
        List<Budget> budgetList= budgetRepository.findAllByStudentAndMonthAndYear(student,month,year);
        List<Expense> expenseList = expenseRepository.findExpensesByStudentAndMonthYear(student, month.getValue(), year);

        return new SpendingReport(student, year, month, budgetList, expenseList);
    }

    @Override
    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
}
