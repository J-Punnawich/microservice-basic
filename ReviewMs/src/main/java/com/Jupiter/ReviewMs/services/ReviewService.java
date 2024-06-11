package com.Jupiter.ReviewMs.services;

import com.Jupiter.ReviewMs.models.Review;
import com.Jupiter.ReviewMs.repositories.ReviewRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

  @Autowired
  ReviewRepository reviewRepository;

  public List<Review> getAllReviews(Long companyId) {
    List<Review> reviews = reviewRepository.findByCompanyId(companyId);

    return reviews;
  }

  public Review getReview(Long reviewId) throws NotFoundException {
    return reviewRepository.findById(reviewId).orElseThrow(NotFoundException::new);
  }

  public boolean addReview(Long companyId, Review review) {

    if (companyId != null && review != null) {
      review.setCompanyId(companyId);
      reviewRepository.save(review);
      return true;
    } else {
      return false;
    }
  }

  public Boolean updateReview(Long reviewId, Review updatedreview) {
    Review review = reviewRepository.findById(reviewId).orElse(null);
    if (review != null) {
      review.setTitle(updatedreview.getTitle());
      review.setDescription(updatedreview.getDescription());
      review.setRating(updatedreview.getRating());
      review.setCompanyId(updatedreview.getCompanyId());
      reviewRepository.save(review);

      return true;
    }
    return false;

  }

  public Boolean deleteReview(Long reviewId) {

    Review review = reviewRepository.findById(reviewId).orElse(null);
    if (review != null) {
      reviewRepository.deleteById(reviewId);
      return true;
    }
    return false;

  }


}
