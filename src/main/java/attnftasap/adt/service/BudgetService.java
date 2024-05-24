package attnftasap.adt.service;

import attnftasap.adt.model.Budget;
import attnftasap.adt.model.Category;
import attnftasap.adt.model.Student;

import java.time.Month;

public interface BudgetService {
    Budget saveBudget(Budget budget);
    Budget findByCategoryAndMonth(Category category, Month month);
    void createBudget(Category category, Month month, int expectedBudget, Student student);
}
