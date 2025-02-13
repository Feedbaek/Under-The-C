package UnderTheC.DeepSea.repository;

import UnderTheC.DeepSea.Entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, String> {
}
