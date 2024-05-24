package attnftasap.adt.service;

import attnftasap.adt.model.Budget;
import attnftasap.adt.model.Category;
import attnftasap.adt.model.Expense;
import attnftasap.adt.model.Student;
import attnftasap.adt.repository.BudgetRepository;
import attnftasap.adt.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static java.time.Month.JANUARY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private BudgetRepository budgetRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testCreateCustomCategory() {
        Student student = new Student("username", "email", "password");

        Category category = new Category(student, "Test Category", "Category Description");

        when(categoryRepository.save(any())).thenReturn(category);

        categoryService.createCategory("Test Category", student);

        verify(categoryRepository, times(1)).save(any());

        assertEquals("Test Category", category.getName());
    }

    @Test
    void deleteCustomCategory() {
        Student student = new Student("username", "email", "password");
        Category category = new Category(student, "Test Category", "Category Description");
        UUID categoryId = category.getCategoryUUID();
        categoryService.deleteCustomCategory(categoryId);
        verify(budgetRepository, times(1)).deleteByCategoryUUID(categoryId);
        verify(categoryRepository, times(1)).deleteCustomCategoryByID(categoryId);
    }

    @Test
    void testSuccessfulFindCategoryFromStudent() {
        Student student = new Student("username", "email", "password");
        String categoryNameBase = "categoryName";
        String categoryDescriptionBase = "categoryDescription";
        List<Category> categoryList = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            Category category = new Category(student, categoryNameBase + i, categoryDescriptionBase + i);
            categoryList.add(category);
            student.addCategory(category);
        }

        assertEquals(categoryList.getFirst(), categoryService.findCategoryFromStudent("categoryName0", student));
        assertEquals(categoryList.get(1), categoryService.findCategoryFromStudent("categoryName1", student));
        assertEquals(categoryList.get(2), categoryService.findCategoryFromStudent("categoryName2", student));
        assertEquals(categoryList.get(3), categoryService.findCategoryFromStudent("categoryName3", student));
    }

    @Test
    void testFailedFindCategoryFromStudent() {
        Student student = new Student("username", "email", "password");
        String categoryNameBase = "categoryName";
        String categoryDescriptionBase = "categoryDescription";
        List<Category> categoryList = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            Category category = new Category(student, categoryNameBase + i, categoryDescriptionBase + i);
            categoryList.add(category);
            student.addCategory(category);
        }

        assertNull(categoryService.findCategoryFromStudent("invalid", student));
    }
}
