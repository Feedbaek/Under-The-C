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
    int recId;
    @Column(length = 20)
    String userId ;
    @Column(length = 20)
    int evaluationId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
