package attnftasap.adt.repository;

import attnftasap.adt.model.Budget;
import attnftasap.adt.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;
import java.util.UUID;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, UUID> {
    List<Budget> findAllByStudentAndMonthAndYear(Student student, Month month, int year);
}
