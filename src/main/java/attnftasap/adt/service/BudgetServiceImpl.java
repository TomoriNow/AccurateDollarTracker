package attnftasap.adt.service;

import attnftasap.adt.model.Budget;
import attnftasap.adt.model.Category;
import attnftasap.adt.model.Student;
import attnftasap.adt.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public void createBudget(Category category, Month month, int expectedBudget, Student student) {
        int currentYear = Year.now().getValue();
        Budget alreadyExisting = budgetRepository.findByCategoryAndMonthAndYearAndStudent(category, month, currentYear, student);
        if (alreadyExisting == null) {
            Budget budget = new Budget();
            budget.setAmount(expectedBudget);
            budget.setMonth(month);
            budget.setYear(2024); // Set the year as per your requirement
            budget.setStudent(student);
            budget.setCategory(category);
            budgetRepository.save(budget);
        } else {
            alreadyExisting.setAmount(expectedBudget);
            budgetRepository.save(alreadyExisting);
        }
    }
    @Override
    public List<Budget> findAllByStudentAndMonthAndYear(Student student, Month month, int year) {
        return budgetRepository.findAllByStudentAndMonthAndYear(student, month, year);
    }
}
