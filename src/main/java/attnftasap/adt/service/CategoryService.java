package attnftasap.adt.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CategoryService {
    void deleteCustomCategory(UUID categoryID);
}
