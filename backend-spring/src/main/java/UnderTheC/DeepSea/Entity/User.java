package UnderTheC.DeepSea.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @Column(length = 15)
    private String id;
    @Column(length = 20)
    private String password;
    @Column(length = 20)
    private String email;
}
