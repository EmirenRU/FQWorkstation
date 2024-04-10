package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Commisioner {

    @Id
    private String fio;
    private String university;
    private String department;
}
