package UnderTheC.DeepSea.repository;

import UnderTheC.DeepSea.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public User findByEmail(String email);
}
