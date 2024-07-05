package saci.application.mappers;

import saci.application.model.Answer;
import saci.domain.model.Knowledge;

public class AnswerMapper {
    public static Answer toResponse(Knowledge knowledge) {
        return Answer.builder()
                .knowledgeId(knowledge.getId().intValue())
                .answer(knowledge.getWeight())
                .build();
    }
}
