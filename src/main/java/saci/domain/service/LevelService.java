package saci.domain.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import saci.domain.model.Level;
import saci.domain.service.exceptions.AlreadyExistsException;
import saci.domain.service.exceptions.CoefficientOverlapException;
import saci.domain.service.exceptions.NotFoundException;
import saci.domain.service.validators.LevelValidator;
import saci.infrastructure.LevelRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LevelService {

    private final LevelRepository levelRepository;

    public Level getLevelById(Long levelId) {
        return levelRepository
                .findById(levelId)
                .orElseThrow(() -> new NotFoundException("Level Not Found"));
    }

    public void deleteLevelById(long levelId) {
        Optional<Level> level = levelRepository.findById(levelId);
        if (level.isPresent()) {
            levelRepository.deleteById(levelId);
        } else {
            String errorMessage = "Level not found with ID: " + levelId;
            throw new NotFoundException(errorMessage);
        }
    }

    public List<Level> getSortedLevelsByRoleIdAsc(Long roleId) {
        List<Level> levels = levelRepository.findSortedLevelsByRoleId(roleId);
        if (levels.isEmpty()) {
            String errorMessage = "No levels found for role ID: " + roleId;
            throw new NotFoundException(errorMessage);
        }
        return levels;
    }

    public Level createLevel(Level level) {
        isOverlappingLevels(level);

        Optional<Level> optionalLevel =
                levelRepository.findByRoleIdAndName(level.getRoleId(), level.getName());
        if (optionalLevel.isPresent()) {
            String errorMessage = "Level name already exists for role ID: " + level.getRoleId();
            log.error(errorMessage);
            throw new AlreadyExistsException(errorMessage);
        }
        return levelRepository.save(level);
    }

    public Optional<Level> findLevelByScore(Long roleId, double score) {
        return levelRepository.findLevelByRoleIdAndScore(roleId, score);
    }

    public Optional<Level> findNextLevelBasedOfScore(Long roleId, double score) {
        return levelRepository.findNextLevelByRoleIdAndScore(roleId, score);
    }

    public Level editLevel(Long levelId, Level updatedLevel) {
        Level existingLevel =
                levelRepository
                        .findById(levelId)
                        .orElseThrow(() -> new NotFoundException("Level not found"));
        isOverlappingLevels(updatedLevel);
        Optional<Level> levelWithSameName =
                levelRepository.findByRoleIdAndName(
                        existingLevel.getRoleId(), updatedLevel.getName());
        if (levelWithSameName.isPresent() && !levelWithSameName.get().getId().equals(levelId)) {
            throw new AlreadyExistsException("Another level with the same name already exists");
        }

        existingLevel.setName(updatedLevel.getName());
        existingLevel.setMinCoefficient(updatedLevel.getMinCoefficient());
        existingLevel.setMaxCoefficient(updatedLevel.getMaxCoefficient());
        existingLevel.setLink(updatedLevel.getLink());

        return levelRepository.save(existingLevel);
    }

    public void isOverlappingLevels(Level level) {
        List<Level> overlappingLevels =
                levelRepository.overlappingLevelsCounter(
                        level.getRoleId(), level.getMinCoefficient(), level.getMaxCoefficient());

        long filteredOverlappingLevels =
                overlappingLevels.stream()
                        .filter(l -> !Objects.equals(level.getId(), l.getId()))
                        .count();

        if (!LevelValidator.levelIsValid(level, filteredOverlappingLevels)) {
            throw new CoefficientOverlapException(
                    "Error Creating the Level: Overlapping Coefficients or Invalid Coefficients");
        }
    }
}
