package com.example.humorie.consultant.review.repository;

import com.example.humorie.consultant.review.entity.Review;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCounselorId(long counselorId);

    @Query("SELECT r FROM Review r WHERE r.counselor.id = :counselorId ORDER BY r.rating LIMIT 1")
    Optional<Review> findTopRatingByCounselorId(@Param(value = "counselorId") long counselorId);
  
    List<Review> findAllByCounselorId(Long counselorId);

    @Transactional
    void deleteByAccount_Id(Long accountId);

}