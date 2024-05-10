package attnftasap.adt.service;

import attnftasap.adt.model.Category;
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
    void deleteCustomCategory() {
        UUID categoryId = UUID.randomUUID();

        categoryService.deleteCustomCategory(categoryId);

        verify(categoryRepository, times(1)).deleteCategoryByCategoryUUID(categoryId);
    }
}
