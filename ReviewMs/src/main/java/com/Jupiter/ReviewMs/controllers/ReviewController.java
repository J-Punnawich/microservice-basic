package com.Jupiter.ReviewMs.controllers;

import com.Jupiter.ReviewMs.messaging.ReviewMessageProducer;
import com.Jupiter.ReviewMs.models.Review;
import com.Jupiter.ReviewMs.services.ReviewService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

  @Autowired
  ReviewService reviewService;
  @Autowired
  ReviewMessageProducer reviewMessageProducer;

  @GetMapping
  public ResponseEntity<List<Review>> getAllReviews(
      @RequestParam(required = false) Long companyId) {
    return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<String> addReview(@RequestParam Long companyId,
      @RequestBody Review review) {
    boolean isReviewSaved = reviewService.addReview(companyId, review);

    if (isReviewSaved) {
      reviewMessageProducer.sendMessage(review);
      return new ResponseEntity<>("Review added", HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>("Review not saved", HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/averageRating")
  public Double getAverageReview(@RequestParam Long companyId) {
    List<Review> reviewList = reviewService.getAllReviews(companyId);

    return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);


  }


}
