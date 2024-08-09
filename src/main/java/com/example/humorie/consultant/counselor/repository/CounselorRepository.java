package com.example.humorie.consultant.counselor.repository;

import com.example.humorie.consultant.counselor.entity.Counselor;
import com.example.humorie.consultant.counselor.entity.Symptom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounselorRepository extends JpaRepository<Counselor, Long>, JpaSpecificationExecutor<Counselor> {

    // 평점 기준으로 상위 4명의 상담사 찾기
    @Query("SELECT c FROM Counselor c join Symptom s on c.id = s.counselor.id WHERE s.symptom = :symptom ORDER BY c.rating DESC LIMIT 4")
    List<Counselor> findTop4ByRating(@Param(value = "symptom")String symptom);
}
