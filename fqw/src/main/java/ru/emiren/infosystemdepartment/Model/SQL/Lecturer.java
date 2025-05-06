package ru.emiren.infosystemdepartment.Model.SQL;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
//import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@RedisHash
public class Lecturer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String academicDegree; // Ученая степень

    private String position; // должность

    @ManyToOne
    @JoinColumn(name="department", referencedColumnName = "code")
    private Department department;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "student_id")
    @JsonManagedReference
    private List<StudentLecturers> students = new ArrayList<>();

}
