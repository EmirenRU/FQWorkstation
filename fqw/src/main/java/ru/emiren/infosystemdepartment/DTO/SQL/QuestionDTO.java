package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionDTO {
    private Long id;
    private String question;
}
