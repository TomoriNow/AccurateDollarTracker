package attnftasap.adt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Month;
import java.util.UUID;

@Entity
@Table(name = "budgets")
@Getter
@Setter
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID budgetId;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "month", nullable = false)
    private Month month;

    @Column(name = "year", nullable = false)
    private int year;

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "categoryUUID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "student", referencedColumnName = "userUUID")
    private Student student;

    public Budget() {}

    public Budget(int amount, Month month, int year, Category category, Student student) {
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.category = category;
        this.student = student;
    }
}
