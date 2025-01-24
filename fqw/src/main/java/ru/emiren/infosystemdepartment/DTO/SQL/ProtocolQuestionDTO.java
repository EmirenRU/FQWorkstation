package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProtocolQuestionDTO {

    private Long id;

    private ProtocolDTO protocol;
    private QuestionDTO question;
}
