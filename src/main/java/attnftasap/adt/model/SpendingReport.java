package attnftasap.adt.model;

import lombok.Getter;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;

@Getter
public class SpendingReport {
    final private Student student;
    final private Month month;
    final private int year;
    final private List<Budget> budgetList;
    final private List<Expense> expenseList;
    private int totalExpectedSpending;
    private int totalSpending;
    final private Map<String, Integer> totalSpendingPerCategory;
    final private Map<Integer, List<Expense>> expensesPerDate;
    final private Map<String, Integer> budgetMap;

    public SpendingReport(Student student, int year, Month month, List<Budget> budgetList, List<Expense> expenseList) {
        this.student = student;
        this.budgetList = budgetList;
        this.expenseList = expenseList;
        this.year = year;
        this.month = month;
        this.totalSpending = 0;
        this.totalExpectedSpending = 0;
        this.totalSpendingPerCategory = new HashMap<>();
        this.expensesPerDate = new HashMap<>();
        this.budgetMap = new HashMap<>();

        for (Budget budget: budgetList) {
            totalExpectedSpending += budget.getAmount();
            budgetMap.put(budget.getCategory().getName(), budget.getAmount());
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

        totalSpendingPerCategory.put(category.getName(), totalSpendingPerCategory.getOrDefault(category.getName(), 0) + cost);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expense.getDate());
        List<Expense> catExpenseList= expensesPerDate.getOrDefault(calendar.get(Calendar.DATE), new ArrayList<>());
        catExpenseList.add(expense);

        expensesPerDate.put(calendar.get(Calendar.DATE), catExpenseList);
    }

}
