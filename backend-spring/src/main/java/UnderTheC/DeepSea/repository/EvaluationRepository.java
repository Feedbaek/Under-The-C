package UnderTheC.DeepSea.repository;

import UnderTheC.DeepSea.Entity.Evaluation;
import UnderTheC.DeepSea.Entity.Evaluation;
import org.springdoc.core.converters.models.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.Date;
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, String> {
    List<Evaluation> findAllByLectureNameOrderByCreatedDesc(String lectureName);
    List<Evaluation> findByLectureName(String lectureName);

    List<Evaluation> findAllByLectureNameAndLectureDivideOrderByCreatedDesc(String lectureName, String lectureDivide);

    List<Evaluation> findAllByLectureNameAndLectureDivideOrderByLikeCountDesc(String lectureName, String lectureDivide);

    List<Evaluation> findAllByLectureNameOrderByLikeCountDesc(String lectureName);

    Optional<Evaluation> findByUserIdAndLectureName(String userId, String lectureName);

}
