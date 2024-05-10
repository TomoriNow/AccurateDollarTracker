    package attnftasap.adt.model;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;
    import java.util.UUID;

    @Entity
    @Getter @Setter
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    @Table(name = "users")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID userUUID;

        @Column(name = "username", nullable = false)
        private String username;

        @Column(name = "email", nullable = false)
        private String email;

        @Column(name = "password", nullable = false)
        private String password;

        public User(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        public User() {}
    }