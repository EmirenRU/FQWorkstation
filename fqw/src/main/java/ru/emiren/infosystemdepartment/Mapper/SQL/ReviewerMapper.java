package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.ReviewerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Reviewer;

public class ReviewerMapper {
    public static Reviewer mapToReviewer(ReviewerDTO reviewerDTO){
        if(reviewerDTO == null) return Reviewer.builder().build();
        return Reviewer.builder()
                .id(reviewerDTO.getId())
                .name(reviewerDTO.getName())
                .academicDegree(reviewerDTO.getAcademicDegree())
                .position(reviewerDTO.getPosition())
                .build();
    }

    public static ReviewerDTO mapToReviewerDTO(Reviewer reviewer){
        if (reviewer == null) { return ReviewerDTO.builder().build(); }
        return ReviewerDTO.builder()
                .id(reviewer.getId())
                .name(reviewer.getName())
                .academicDegree(reviewer.getAcademicDegree())
                .position(reviewer.getPosition())
                .build();
    }
}
