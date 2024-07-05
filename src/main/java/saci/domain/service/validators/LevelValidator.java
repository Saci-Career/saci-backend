package saci.domain.service.validators;

import lombok.NoArgsConstructor;
import saci.domain.model.Level;

@NoArgsConstructor
public final class LevelValidator {
    public static boolean maxCoefficientIsValid(Level newLevel) {
        return newLevel.getMaxCoefficient() > 0 && newLevel.getMaxCoefficient() <= 100;
    }

    public static boolean minCoefficientIsValid(Level newLevel) {
        return newLevel.getMinCoefficient() >= 0 && newLevel.getMinCoefficient() <= 100;
    }

    public static boolean levelsAreOverlapping(long overlappingLevelsCounter) {
        return overlappingLevelsCounter != 0;
    }

    public static boolean levelIsValid(Level newLevel, long overlappingLevelsCounter) {
        return maxCoefficientIsValid(newLevel)
                && minCoefficientIsValid(newLevel)
                && !levelsAreOverlapping(overlappingLevelsCounter);
    }
}
