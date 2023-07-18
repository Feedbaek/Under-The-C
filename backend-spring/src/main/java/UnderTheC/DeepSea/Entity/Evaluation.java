package UnderTheC.DeepSea.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "evaluation")
@Getter
@Setter
public class Evaluation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(length = 20)
    private int evaluationId;

    @Column(length = 50)
    private String userId;
    @Column(length = 50)
    private String lectureName;
    @Column(length = 50)
    private String professorName;
    @Column(length = 20)
    private int lectureYear;
    @Column(length = 20)
    private String semesterDivide;
    @Column(length = 10)
    private String lectureDivide;
    @Column(length = 50)
    private String evaluationTitle;
    @Column(length = 2048)
    private String evaluationContent;
    @Column(length = 10)
    private String totalScore;
    @Column(length = 10)
    private String creditScore;
    @Column(length = 10)
    private String comfortableScore;
    @Column(length = 10)
    private String lectureScore;
    @Column(length = 20)
    private int likeCount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

}
