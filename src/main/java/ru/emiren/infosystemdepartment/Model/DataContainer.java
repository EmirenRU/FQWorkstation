package ru.emiren.infosystemdepartment.Model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataContainer {
    private Student     student;
    private Lecturer    lecturer;
    private Orientation orientation;
}
