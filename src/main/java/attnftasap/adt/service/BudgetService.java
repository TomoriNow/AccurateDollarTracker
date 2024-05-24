package attnftasap.adt.service;

import attnftasap.adt.model.Budget;
import attnftasap.adt.model.Category;
import attnftasap.adt.model.Student;

import java.time.Month;
import java.util.List;

public interface BudgetService {
    void createBudget(Category category, Month month, int expectedBudget, Student student);

    List<Budget> findAllByStudentAndMonthAndYear(Student student, Month month, int year);
}
