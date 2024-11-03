package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orientation {
    @Id
    private String code;
    private String name;

    @OneToMany(mappedBy = "orientation", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Protection> protection = new ArrayList<>();
}
