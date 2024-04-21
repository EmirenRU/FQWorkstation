package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Builder
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Protection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orientation_code", referencedColumnName = "code")
    private Orientation orientation;          // Направление

    private LocalDate dateOfProtection;
}
