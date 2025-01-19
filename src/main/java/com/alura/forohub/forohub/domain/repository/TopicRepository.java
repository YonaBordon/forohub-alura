package com.alura.forohub.forohub.domain.repository;

import com.alura.forohub.forohub.domain.entity.Topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicRepository extends JpaRepository<Topic, Long> {

  Page<Topic> findByCourse(String course, Pageable pageable);

  @Query("SELECT t FROM Topic t WHERE FUNCTION('YEAR', t.creationDate) = :year")
  Page<Topic> findByYear(@Param("year") Integer year, Pageable pageable);

  @Query("SELECT t FROM Topic t WHERE FUNCTION('YEAR', t.creationDate) = :year AND t.course = :course")
  Page<Topic> findByCourseAndYear(@Param("course") String course, @Param("year") Integer year, Pageable pageable);

  boolean existsByTitleAndMessage(String title, String message);
}