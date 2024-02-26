package ru.emiren.infosystemdepartment.Model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Department implements Serializable {

    @Id
    private String code;

    private String name;


}
