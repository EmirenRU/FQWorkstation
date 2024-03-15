package ru.emiren.infosystemdepartment.Model.SQL;


import jakarta.persistence.*;
import lombok.*;
import ru.emiren.infosystemdepartment.Mapper.StudentMapper;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FQW {

    @Id
    private String name;

    private String classifier; // классификатор

    private Float uniqueness; // оригинальность
    private String feedback; // отзыв
    private String volume; // объем

    @OneToOne
    @JoinColumn(name = "reviewer_id", referencedColumnName = "id")
    private Reviewer reviewer;

}
