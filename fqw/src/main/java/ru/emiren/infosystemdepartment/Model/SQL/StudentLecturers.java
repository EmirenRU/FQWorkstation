package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import lombok.*;
//import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@RedisHash
public class StudentLecturers implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")

    private Student student;

    @ManyToOne
    @JoinColumn(name = "lecturer_id", referencedColumnName = "id")
    private Lecturer lecturer;

    private Boolean isScientificSupervisor;
    private Boolean isConsultant;
}
