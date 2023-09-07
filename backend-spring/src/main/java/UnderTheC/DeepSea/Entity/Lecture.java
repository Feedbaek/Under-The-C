package UnderTheC.DeepSea.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lecture")
@Getter
@Setter
public class Lecture {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Id
    @Column(length = 20)
    private String lecture_name;
    @Column(length = 20)
    private String professor_name;
    @Column(length = 20)
    private String semester_divide;
    @Column(length = 20)
    private String lecture_divide;
}
