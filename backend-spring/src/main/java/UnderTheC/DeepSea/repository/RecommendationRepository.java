package UnderTheC.DeepSea.repository;

import UnderTheC.DeepSea.Entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, String> {


}
