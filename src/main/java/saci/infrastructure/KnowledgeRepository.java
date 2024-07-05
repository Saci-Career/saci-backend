package saci.infrastructure;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saci.domain.model.Knowledge;

@Repository
public interface KnowledgeRepository extends JpaRepository<Knowledge, Long> {
    Optional<Knowledge> findByName(String name);

    List<Knowledge> findByRoleId(Long roleId);
}
