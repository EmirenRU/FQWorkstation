package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.FQWDTO;
import ru.emiren.infosystemdepartment.Model.SQL.FQW;

public class FQWMapper {
    public static FQW mapToFQW(FQWDTO fqwdto){
        return FQW.builder()
                .name(fqwdto.getName())
                .classifier(fqwdto.getClassifier())
                .uniqueness(fqwdto.getUniqueness())
                .feedback(fqwdto.getFeedback())
                .volume(fqwdto.getVolume())
                .reviewer(fqwdto.getReviewer())
                .build();
    }

    public static FQWDTO mapToFQWDTO(FQW fqw){
        return FQWDTO.builder()
                .name(fqw.getName())
                .classifier(fqw.getClassifier())
                .uniqueness(fqw.getUniqueness())
                .feedback(fqw.getFeedback())
                .volume(fqw.getVolume())
                .reviewer(fqw.getReviewer())
                .build();
    }
}
