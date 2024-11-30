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
}
