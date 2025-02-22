package ru.emiren.infosystemdepartment.Model.SQL;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FQW {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "decree_id",referencedColumnName = "id")
    private Decree decree;

    private String classifier; // классификатор
    private Float uniqueness; // оригинальность
    private String feedback; // отзыв
    private String volume; // объем

    @OneToOne
    @JoinColumn(name = "reviewer_id", referencedColumnName = "id")
    private Reviewer reviewer;

}
