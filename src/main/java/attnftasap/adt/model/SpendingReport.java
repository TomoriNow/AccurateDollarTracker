package attnftasap.adt.model;

import lombok.Getter;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class SpendingReport {
    final private Student student;
    final private Month month;
    final private int year;
    final private List<Budget> budgetList;
    final private List<Expense> expenseList;
    private int totalExpectedSpending;
    private int totalSpending;
    final private Map<Category, Integer> totalSpendingPerCategory;

    public SpendingReport(Student student, int year, Month month, List<Budget> budgetList, List<Expense> expenseList) {
        this.student = student;
        this.budgetList = budgetList;
        this.expenseList = expenseList;
        this.year = year;
        this.month = month;
        this.totalSpending = 0;
        this.totalExpectedSpending = 0;
        this.totalSpendingPerCategory = new HashMap<>();

        for (Budget budget: budgetList) {
            totalExpectedSpending += budget.getAmount();
        }

        for (Expense expense: expenseList) {
            updateAfterAddExpense(expense);
        }
    }

    public void addExpense(Expense expense) {
        expenseList.add(expense);
        updateAfterAddExpense(expense);
    }

    private void updateAfterAddExpense(Expense expense) {
        int cost = expense.getCost();
        Category category = expense.getCategory();
        totalSpending += cost;

        totalSpendingPerCategory.put(category, totalSpendingPerCategory.getOrDefault(category, 0) + cost);
    }
}
