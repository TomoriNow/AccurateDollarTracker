package attnftasap.adt.service;

import attnftasap.adt.model.Budget;
import attnftasap.adt.model.Category;
import attnftasap.adt.model.Student;
import attnftasap.adt.repository.CategoryRepository;
import attnftasap.adt.repository.BudgetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetService budgetService;

    @Transactional
    public void createCategory(String categoryName, Month month, int expectedBudget, Student student) {
        Category category = new Category(student, categoryName);
        categoryRepository.save(category);

        Budget budget = new Budget();
        budget.setAmount(expectedBudget);
        budget.setMonth(month);
        budget.setYear(2024);
        budget.setStudent(student);
        budget.setCategory(category);

        budgetRepository.save(budget);
    }

    @Override
    @Transactional
    public void deleteCustomCategory(UUID categoryId) {
        categoryRepository.deleteCustomCategoryByID(categoryId);
    }

    @Override
    public Category findCategoryFromStudent(String name, Student student) {
        List<Category> categoryList = student.getCategories();
        for (Category category: categoryList) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }
    @Override
    public List<Category> findAllCategoriesForStudentsByMonth(Student student, Month month) {
        List<Category> allCategories = categoryRepository.findByStudent(student);
        return allCategories.stream()
                .filter(category -> {
                    Budget budget = budgetService.findByCategoryAndMonth(category, month);
                    return budget != null && budget.getMonth().equals(month);
                })
                .collect(Collectors.toList());
    }
}
