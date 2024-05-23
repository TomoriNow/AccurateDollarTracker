package attnftasap.adt.service;

import attnftasap.adt.model.Budget;
import attnftasap.adt.model.Category;

import java.time.Month;

public interface BudgetService {
    Budget saveBudget(Budget budget);
    Budget findByCategoryAndMonth(Category category, Month month);
}
