package ru.emiren.infosystemdepartment.Model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Department {
    @Id
    private String code;
    private String name;
}
