package attnftasap.adt.service;

import attnftasap.adt.model.Budget;
import attnftasap.adt.model.Category;
import attnftasap.adt.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;

@Service
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Budget saveBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public Budget findByCategoryAndMonth(Category category, Month month) {
        return budgetRepository.findByCategoryAndMonth(category, month);
    }
}
