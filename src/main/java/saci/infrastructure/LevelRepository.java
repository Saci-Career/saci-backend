package saci.infrastructure;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import saci.domain.model.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    Optional<Level> findByName(String name);

    Optional<Level> findByRoleIdAndName(Long roleId, String name);

    @Query(
            "SELECT l FROM Level l WHERE l.roleId = :roleId AND "
                    + "(l.minCoefficient <= :maxCoefficient AND l.maxCoefficient >= :minCoefficient)")
    List<Level> overlappingLevelsCounter(
            @Param("roleId") Long roleId,
            @Param("minCoefficient") Integer minCoefficient,
            @Param("maxCoefficient") Integer maxCoefficient);

    @Query(
            "SELECT l FROM Level l WHERE l.roleId = :roleId AND :score BETWEEN CAST(l.minCoefficient AS double) AND CAST(l.maxCoefficient AS double)")
    Optional<Level> findLevelByRoleIdAndScore(
            @Param("roleId") Long roleId, @Param("score") double score);

    @Query(
            "SELECT l FROM Level l WHERE l.roleId = :roleId AND CAST(l.minCoefficient AS double) > :score ORDER BY l.minCoefficient ASC LIMIT 1")
    Optional<Level> findNextLevelByRoleIdAndScore(
            @Param("roleId") Long roleId, @Param("score") double score);

    @Query("SELECT l FROM Level l WHERE l.roleId = :roleId ORDER BY l.minCoefficient ASC")
    List<Level> findSortedLevelsByRoleId(@Param("roleId") Long roleId);
}
