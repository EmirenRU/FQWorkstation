package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.ReviewerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Reviewer;

import java.util.List;

public interface ReviewerService {
    Reviewer saveReviewer(Reviewer reviewer);
    void deleteReviewer(Reviewer reviewer);
    List<ReviewerDTO> getAllReviewer();

    Reviewer    getReviewer(Long id);
    ReviewerDTO getReviewerDTO(Long id);
    Reviewer    updateReviewer(Reviewer reviewer);
}
