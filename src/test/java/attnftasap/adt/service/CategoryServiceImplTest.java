package attnftasap.adt.service;

import attnftasap.adt.model.Budget;
import attnftasap.adt.model.Category;
import attnftasap.adt.model.Expense;
import attnftasap.adt.model.Student;
import attnftasap.adt.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testCreateCustomCategory() {
        Student student = new Student("username", "email", "password");

        Category category = new Category(student, "Test Category", "Category Description");

        when(categoryRepository.save(any())).thenReturn(category);

        categoryService.createCategory("Test Category");

        verify(categoryRepository, times(1)).save(any());

        assertEquals("Test Category", category.getName());
    }

    @Test
    void deleteCustomCategory() {
        UUID categoryId = UUID.randomUUID();

        categoryService.deleteCustomCategory(categoryId);

        verify(categoryRepository, times(1)).deleteCategoryByCategoryUUID(categoryId);
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
