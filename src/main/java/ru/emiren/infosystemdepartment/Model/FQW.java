package ru.emiren.infosystemdepartment.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FQW {

    @Id
    private String name; // название

    private String classifier; // классификатор

    private Float uniqueness; // оригинальность
    private String feedback; // отзыв
    private String volume; // объем

    @OneToOne
    @JoinColumn(name = "reviewer_id", referencedColumnName = "id")
    private Reviewer reviewer;

}
