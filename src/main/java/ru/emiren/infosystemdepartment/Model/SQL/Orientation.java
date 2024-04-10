package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orientation {
    @Id
    private String code;
    private String name;
}
