package attnftasap.adt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "expenses")
@Getter @Setter
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID expenseId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cost", nullable = false)
    private int cost;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "categoryUUID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "student", referencedColumnName = "userUUID")
    private Student student;

    public Expense() {}

    public Expense(String name, int cost, Date date, Category category, Student student) {
        this.name = name;
        this.cost = cost;
        this.date = date;
        this.category = category;
        this.student = student;
    }
}
