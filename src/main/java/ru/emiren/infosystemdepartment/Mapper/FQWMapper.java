package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.FQWDTO;
import ru.emiren.infosystemdepartment.Model.SQL.FQW;

public class FQWMapper {
    public static FQW mapToFQW(FQWDTO fqwdto){
        return FQW.builder()
//                .name(fqwdto.getName)
//                .id(fqwdto.getId())
//                .student(fqwdto.getStudent())
                .classifier(fqwdto.getClassifier())
                .uniqueness(fqwdto.getUniqueness())
                .feedback(fqwdto.getFeedback())
                .volume(fqwdto.getVolume())
                .reviewer(fqwdto.getReviewer())
                .build();
    }

    public static FQWDTO mapToFQWDTO(FQWDTO fqwdto){
        return FQWDTO.builder()
                .id(fqwdto.getId())
                .student(fqwdto.getStudent())
                .classifier(fqwdto.getClassifier())
                .uniqueness(fqwdto.getUniqueness())
                .feedback(fqwdto.getFeedback())
                .volume(fqwdto.getVolume())
                .reviewer(fqwdto.getReviewer())
                .build();
    }
}
