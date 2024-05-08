package attnftasap.adt.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Embeddable @Getter @Setter
public class CategoryKey implements Serializable {
    private UUID studentId;
    private String name;

    public CategoryKey() {}
    public CategoryKey(UUID studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }
}
