package attnftasap.adt.service;

import attnftasap.adt.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void deleteCustomCategory(UUID categoryId) {
        categoryRepository.deleteCategoryByCategoryUUID(categoryId);
    }
}
