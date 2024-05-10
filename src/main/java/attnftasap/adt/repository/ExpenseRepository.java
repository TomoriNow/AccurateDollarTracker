package attnftasap.adt.repository;


import attnftasap.adt.model.Expense;
import attnftasap.adt.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    @Query("SELECT e FROM Expense e WHERE e.student = :student AND YEAR(e.date) = :year AND MONTH(e.date) = :month")
    List<Expense> findExpensesByStudentAndMonthYear(@Param("student") Student student, @Param("month") int month, @Param("year") int year);

    List<Expense> findAllByStudent(Student student);
}
