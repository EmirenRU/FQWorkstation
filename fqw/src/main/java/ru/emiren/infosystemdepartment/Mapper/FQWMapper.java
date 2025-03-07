package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.Payload.Selector.ThemeSelector;
import ru.emiren.infosystemdepartment.DTO.SQL.FQWDTO;
import ru.emiren.infosystemdepartment.Model.SQL.FQW;

public class FQWMapper {
    public static FQW mapToFQW(FQWDTO fqwdto){
        return FQW.builder()
                .id(fqwdto.getId())
                .decree(DecreeMapper.mapToDecree(fqwdto.getDecree()))
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
                .decree(DecreeMapper.mapToDecreeDTO(fqw.getDecree()))
                .classifier(fqw.getClassifier())
                .uniqueness(fqw.getUniqueness())
                .feedback(fqw.getFeedback())
                .volume(fqw.getVolume())
                .reviewer(ReviewerMapper.mapToReviewerDTO(fqw.getReviewer()))
                .build();
    }

    public static ThemeSelector mapToSelector(FQWDTO fqwdto){
        return ThemeSelector
                .builder()
                .value(fqwdto.getDecree().getId().toString())
                .name(fqwdto.getDecree().getTheme())
                .build();
    }
}
