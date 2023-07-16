package UnderTheC.DeepSea.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
@Entity
@Table(name = "recommendation")
@Getter
@Setter
public class Recommendation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int recID;
    @Column(length = 20)
    String userID ;
    @Column(length = 20)
    int evaluationID;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
