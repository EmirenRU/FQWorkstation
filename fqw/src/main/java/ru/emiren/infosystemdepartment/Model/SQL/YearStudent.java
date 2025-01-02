package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Entity(name = "year_student")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class YearStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "year_date", referencedColumnName = "year")
    private Year year;

    @Override
    public String toString() {
        return "YearStudent[id = " + id + ", student = " + student + ", year = " + year + "]";
    }
}
