package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.QuestionDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Question;

public class QuestionMapper {
    public static Question mapToQuestion(QuestionDTO questionDTO){
        return Question.builder()
                .id(questionDTO.getId())
                .question(questionDTO.getQuestion())
                .build();
    }

    public static QuestionDTO mapToQuestionDTO(Question question){
        return QuestionDTO.builder()
                .id(question.getId())
                .question(question.getQuestion())
                .build();
    }
}
