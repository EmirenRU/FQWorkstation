package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.ReviewerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Reviewer;

public class ReviewerMapper {
    public static Reviewer mapToReviewer(ReviewerDTO reviewerDTO){
        return Reviewer.builder()
                .id(reviewerDTO.getId())
                .fio(reviewerDTO.getFio())
                .academicDegree(reviewerDTO.getAcademicDegree())
                .position(reviewerDTO.getPosition())
                .build();
    }

    public static ReviewerDTO mapToReviewerDTO(Reviewer reviewer){
        return ReviewerDTO.builder()
                .id(reviewer.getId())
                .fio(reviewer.getFio())
                .academicDegree(reviewer.getAcademicDegree())
                .position(reviewer.getPosition())
                .build();
    }
}
