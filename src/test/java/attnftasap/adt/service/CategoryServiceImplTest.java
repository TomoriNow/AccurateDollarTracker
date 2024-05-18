package attnftasap.adt.service;

import attnftasap.adt.model.Category;
import attnftasap.adt.model.Student;
import attnftasap.adt.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        Student student = new Student("username", "email", "password");
        Category category = new Category(student, "Test Category", "Category Description");
        UUID categoryId = category.getCategoryUUID();
        categoryService.deleteCustomCategory(categoryId);
        verify(categoryRepository, times(1)).deleteCategoryByCategoryUUID(categoryId);
    }
}
