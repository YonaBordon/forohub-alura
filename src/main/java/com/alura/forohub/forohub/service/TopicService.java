package com.alura.forohub.forohub.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alura.forohub.forohub.domain.entity.Topic;
import com.alura.forohub.forohub.domain.repository.TopicRepository;
import com.alura.forohub.forohub.dto.ApiResponse;
import com.alura.forohub.forohub.dto.TopicRequestDto;
import com.alura.forohub.forohub.dto.TopicResponseDto;

import jakarta.transaction.Transactional;

@Service
public class TopicService {
  @Autowired
  private final TopicRepository topicRepository;

  public TopicService(TopicRepository topicRepository) {
    this.topicRepository = topicRepository;
  }

  private TopicResponseDto convertToDto(Topic topic) {
    TopicResponseDto dto = new TopicResponseDto();
    dto.setId(topic.getId());
    dto.setTitle(topic.getTitle());
    dto.setMessage(topic.getMessage());
    dto.setCreationDate(topic.getCreationDate());
    dto.setStatus(topic.getStatus());
    dto.setAuthor(topic.getAuthor());
    dto.setCourse(topic.getCourse());
    dto.setStatus(topic.getStatus());
    dto.setCreationDate(topic.getCreationDate());
    return dto;
  }

  @Transactional
  public ApiResponse<TopicResponseDto> createTopic(TopicRequestDto requestDto) {
    if (topicRepository.existsByTitleAndMessage(requestDto.getTitle(), requestDto.getMessage())) {
      return new ApiResponse<>(false, 400, "El tópico ya existe", null);
    }

    Topic topic = new Topic();
    topic.setTitle(requestDto.getTitle());
    topic.setMessage(requestDto.getMessage());
    topic.setAuthor(requestDto.getAuthor());
    topic.setCourse(requestDto.getCourse());

    topic.setStatus("ACTIVO");
    topic.setCreationDate(LocalDateTime.now());

    topic = topicRepository.save(topic);

    TopicResponseDto responseDto = convertToDto(topic);

    return new ApiResponse<>(true, 201, "Tópico creado exitosamente.", List.of(responseDto));
  }

  public ApiResponse<TopicResponseDto> getAllTopics(String course, Integer year, Pageable pageable) {
    Page<Topic> topics;

    if (course == null && year == null) {
      topics = topicRepository.findAll(pageable);
    } else if (course != null && year == null) {
      topics = topicRepository.findByCourse(course, pageable);
    } else if (course == null && year != null) {
      topics = topicRepository.findByYear(year, pageable);
    } else {
      topics = topicRepository.findByCourseAndYear(course, year, pageable);
    }

    List<TopicResponseDto> response = topics.getContent().stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());

    return new ApiResponse<TopicResponseDto>(true, 200, "Tópicos obtenidos exitosamente", response);
  }

  @Transactional
  public ApiResponse<TopicResponseDto> getTopicDetails(Long id) {
    Topic topic = topicRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));

    TopicResponseDto responseDto = convertToDto(topic);

    return new ApiResponse<>(true, 200, "Tópico obtenido exitosamente", List.of(responseDto));
  }

  @Transactional
  public ApiResponse<TopicResponseDto> updateTopic(Long id, TopicRequestDto requestDto) {
    Optional<Topic> optionalTopic = topicRepository.findById(id);
    if (!optionalTopic.isPresent()) {
      throw new RuntimeException("Tópico no encontrado");
    }

    Topic topic = optionalTopic.get();
    topic.setTitle(requestDto.getTitle());
    topic.setMessage(requestDto.getMessage());
    topic.setAuthor(requestDto.getAuthor());
    topic.setCourse(requestDto.getCourse());

    topic = topicRepository.save(topic);
    TopicResponseDto responseDto = convertToDto(topic);

    return new ApiResponse<>(true, 200, "Tópico actualizado exitosamente.", List.of(responseDto));
  }

  @Transactional
  public ApiResponse<Void> deleteTopic(Long id) {
    Optional<Topic> topicOptional = topicRepository.findById(id);

    if (!topicOptional.isPresent()) {
      return new ApiResponse<>(false, 404, "Tópico no encontrado.", null);
    }

    topicRepository.deleteById(id);
    return new ApiResponse<>(true, 200, "Tópico eliminado exitosamente.", null);
  }

}
