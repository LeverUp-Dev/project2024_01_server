package university_team.project2024_01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import university_team.project2024_01.entity.Member;

public interface UserRepository extends JpaRepository<Member, Long> {

    Boolean existsByUsername(String username);
}
