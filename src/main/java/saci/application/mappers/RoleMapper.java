package saci.application.mappers;

import java.util.stream.Collectors;
import saci.application.model.ScoreRequest;
import saci.domain.model.Role;

public class RoleMapper {
    public static Role toDomain(ScoreRequest request) {
        return Role.builder()
                .id(request.getRoleId())
                .knowledges(
                        request.getAnswers().stream()
                                .map(KnowledgeMapper::toDomain)
                                .collect(Collectors.toList()))
                .build();
    }
}
