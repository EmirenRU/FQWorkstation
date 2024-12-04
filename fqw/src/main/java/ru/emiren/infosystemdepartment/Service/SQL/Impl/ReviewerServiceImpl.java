package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.ReviewerDTO;
import ru.emiren.infosystemdepartment.Mapper.SQL.ReviewerMapper;
import ru.emiren.infosystemdepartment.Model.SQL.Reviewer;
import ru.emiren.infosystemdepartment.Repository.SQL.ReviewerRepository;
import ru.emiren.infosystemdepartment.Service.SQL.ReviewerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewerServiceImpl implements ReviewerService {

    private ReviewerRepository reviewerRepository;

    @Autowired
    public ReviewerServiceImpl(ReviewerRepository reviewerRepository) {
        this.reviewerRepository = reviewerRepository;
    }

    @Override
    public Reviewer saveReviewer(Reviewer reviewer) {
        return reviewerRepository.save(reviewer);
    }

    @Override
    public void deleteReviewer(Reviewer reviewer) {
        reviewerRepository.delete(reviewer);
    }

    @Override
    public List<ReviewerDTO> getAllReviewer() {
        return reviewerRepository.findAll()
                .stream()
                .map(ReviewerMapper::mapToReviewerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Reviewer getReviewer(Long id) {
        return reviewerRepository.findById(id).orElse(null);
    }

    @Override
    public ReviewerDTO getReviewerDTO(Long id) {
        return reviewerRepository.findById(id)
                .map(ReviewerMapper::mapToReviewerDTO)
                .orElse(null);
    }

    @Override
    public Reviewer updateReviewer(Reviewer reviewer) {
        Reviewer upd = getReviewer(reviewer.getId());

        if (upd == null) { return reviewerRepository.save(reviewer); }

        if (reviewer.getName() != null) upd.setName(reviewer.getName());
        if (reviewer.getPosition() != null) upd.setPosition(reviewer.getPosition());
        if (reviewer.getAcademicDegree() != null) upd.setAcademicDegree(reviewer.getAcademicDegree());

        return reviewerRepository.save(upd);
    }
}
