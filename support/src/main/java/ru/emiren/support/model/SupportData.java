package ru.emiren.support.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity(name = "data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupportData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String fullName;
    private String email;
    private String phone;
    @NotNull
    private String description;
}
