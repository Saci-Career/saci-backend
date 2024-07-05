package saci.application.mappers;

import saci.application.model.Answer;
import saci.domain.model.Knowledge;

public class KnowledgeMapper {
    public static Knowledge toDomain(Answer answer) {
        return Knowledge.builder()
                .id(answer.getKnowledgeId().longValue())
                .weight(answer.getAnswer())
                .build();
    }
}
