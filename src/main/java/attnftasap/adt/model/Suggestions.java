package attnftasap.adt.model;
import jakarta.persistence.*;

@Entity
@Table(name = "suggestions")
public class Suggestions {

    @Id
    private String uuid;
    private String childUuid;
    private String guardianUuid;
    private String suggestion;

    // Constructors, getters, and setters

    public Suggestions() {
    }

    public Suggestions(String uuid, String childUuid, String guardianUuid, String suggestion) {
        this.uuid = uuid;
        this.childUuid = childUuid;
        this.guardianUuid = guardianUuid;
        this.suggestion = suggestion;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getChildUuid() {
        return childUuid;
    }

    public void setChildUuid(String childUuid) {
        this.childUuid = childUuid;
    }

    public String getGuardianUuid() {
        return guardianUuid;
    }

    public void setGuardianUuid(String guardianUuid) {
        this.guardianUuid = guardianUuid;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
