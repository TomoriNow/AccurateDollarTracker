package attnftasap.adt.repository;

import attnftasap.adt.model.Category;
import attnftasap.adt.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Category c WHERE c.categoryUUID = :categoryUUID")
    void deleteCustomCategoryByID(@Param("categoryUUID") UUID categoryUUID);

    List<Category> findByStudent(Student student);
}