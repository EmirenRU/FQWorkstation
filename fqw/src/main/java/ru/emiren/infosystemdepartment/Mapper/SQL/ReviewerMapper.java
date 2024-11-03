package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.ReviewerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Reviewer;

public class ReviewerMapper {
    public static Reviewer mapToReviewer(ReviewerDTO reviewerDTO){
        return Reviewer.builder()
                .id(reviewerDTO.getId())
                .name(reviewerDTO.getName())
                .academicDegree(reviewerDTO.getAcademicDegree())
                .position(reviewerDTO.getPosition())
                .build();
    }

    public static ReviewerDTO mapToReviewerDTO(Reviewer reviewer){
        return ReviewerDTO.builder()
                .id(reviewer.getId())
                .name(reviewer.getName())
                .academicDegree(reviewer.getAcademicDegree())
                .position(reviewer.getPosition())
                .build();
    }
}
