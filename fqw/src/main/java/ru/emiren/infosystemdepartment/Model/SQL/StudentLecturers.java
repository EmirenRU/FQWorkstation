package ru.emiren.infosystemdepartment.Model.SQL;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "student_lecturers")
public class StudentLecturers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @JsonBackReference
    private Student student;

    @ManyToOne
    @JoinColumn(name = "lecturer_id", referencedColumnName = "id")
    @JsonBackReference
    private Lecturer lecturer;

    private Boolean isScientificSupervisor;
    private Boolean isConsultant;
}
