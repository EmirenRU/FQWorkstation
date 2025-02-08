package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.FQWDTO;
import ru.emiren.infosystemdepartment.Model.SQL.FQW;

public class FQWMapper {
    public static FQW mapToFQW(FQWDTO fqwdto){
        return FQW.builder()
                .id(fqwdto.getId())
                .name(fqwdto.getName())
                .classifier(fqwdto.getClassifier())
                .uniqueness(fqwdto.getUniqueness())
                .feedback(fqwdto.getFeedback())
                .volume(fqwdto.getVolume())
                .reviewer(ReviewerMapper.mapToReviewer(fqwdto.getReviewer()))
                .build();
    }

    public static FQWDTO mapToFQWDTO(FQW fqw){
        return FQWDTO.builder()
                .id(fqw.getId())
                .name(fqw.getName())
                .classifier(fqw.getClassifier())
                .uniqueness(fqw.getUniqueness())
                .feedback(fqw.getFeedback())
                .volume(fqw.getVolume())
                .reviewer(ReviewerMapper.mapToReviewerDTO(fqw.getReviewer()))
                .build();
    }
}
