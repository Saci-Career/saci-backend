package saci.domain.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import saci.application.mappers.RoleMapper;
import saci.application.model.Answer;
import saci.application.model.ScoreRequest;
import saci.application.model.ScoreResponse;
import saci.domain.model.Knowledge;
import saci.domain.model.Level;
import saci.domain.model.Role;
import saci.infrastructure.LevelRepository;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final RoleService roleService;
    private final KnowledgeService knowledgeService;
    private final LevelService levelService;
    private final LevelRepository levelRepository;

    private static final int MAX_WEIGHT = 5;
    private static final int PERCENTAGE = 100;

    public ScoreResponse evaluateAnswer(ScoreRequest request) {
        Role role = RoleMapper.toDomain(request);

        List<Knowledge> knowledges = knowledgeService.getKnowledgesByRoleId(role.getId());

        double totalWeightedScore = 0.0;
        double totalWeight = 0.0;

        for (Knowledge knowledge : knowledges) {
            for (Answer answer : request.getAnswers()) {
                if (knowledge.getId().intValue() == answer.getKnowledgeId()) {
                    totalWeightedScore += answer.getAnswer() * knowledge.getWeight();
                    totalWeight += knowledge.getWeight();
                    break;
                }
            }
        }

        if (totalWeight == 0) {
            throw new IllegalArgumentException("Total weight is zero, invalid request.");
        }

        int score = (int) ((totalWeightedScore / (MAX_WEIGHT * totalWeight)) * PERCENTAGE);

        Optional<Level> currentLevel = levelService.findLevelByScore(role.getId(), score);
        Optional<Level> nextLevel = levelService.findNextLevelBasedOfScore(role.getId(), score);

        return ScoreResponse.builder().currentLevel(currentLevel).nextLevel(nextLevel).build();
    }
}
