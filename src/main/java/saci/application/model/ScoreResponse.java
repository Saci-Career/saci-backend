package saci.application.model;

import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import saci.domain.model.Level;

@Getter
@Builder
public class ScoreResponse {
    private Optional<Level> currentLevel;
    private Optional<Level> nextLevel;
}
