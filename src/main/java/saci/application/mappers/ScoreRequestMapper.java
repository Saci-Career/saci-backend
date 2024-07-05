package saci.application.mappers;

import java.util.stream.Collectors;
import saci.application.model.ScoreRequest;
import saci.domain.model.Role;

public class ScoreRequestMapper {
    public static ScoreRequest toResponse(Role role) {
        return ScoreRequest.builder()
                .roleId(role.getId())
                .answers(
                        role.getKnowledges().stream()
                                .map(AnswerMapper::toResponse)
                                .collect(Collectors.toList()))
                .build();
    }
}
