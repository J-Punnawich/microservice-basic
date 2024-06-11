package com.Jupiter.ReviewMs.repositories;

import com.Jupiter.ReviewMs.models.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findByCompanyId(Long companyId);
}
