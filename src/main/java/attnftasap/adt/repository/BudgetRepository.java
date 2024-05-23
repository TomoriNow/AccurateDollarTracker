package attnftasap.adt.repository;

import attnftasap.adt.model.Budget;
import attnftasap.adt.model.Category;
import attnftasap.adt.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;
import java.util.UUID;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, UUID> {
    List<Budget> findAllByStudentAndMonthAndYear(Student student, Month month, int year);
    Budget findByCategoryAndMonth(Category category, Month month);
    @Modifying
    @Transactional
    @Query("DELETE FROM Budget b WHERE b.category.categoryUUID = :categoryUUID")
    void deleteByCategoryUUID(@Param("categoryUUID") UUID categoryUUID);

    Budget findByCategoryAndMonthAndYearAndStudent(Category category, Month month, int year, Student student);
}
