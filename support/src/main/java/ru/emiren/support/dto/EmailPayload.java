package ru.emiren.support.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailPayload {
    String to;
    String subject;
    String body;
}
