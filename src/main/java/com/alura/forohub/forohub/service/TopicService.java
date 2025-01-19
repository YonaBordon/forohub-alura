package com.alura.forohub.forohub.service;

import java.time.LocalDateTime;
import java.util.List;
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

@Transactional
public ApiResponse<TopicResponseDto> createTopic(TopicRequestDto requestDto) {
  // Verificar si el tópico ya existe
  if (topicRepository.existsByTitleAndMessage(requestDto.getTitle(), requestDto.getMessage())) {
    return new ApiResponse<>(false, 400, "El tópico ya existe", null);
  }

  // Crear un nuevo objeto Topic
  Topic topic = new Topic();
  topic.setTitle(requestDto.getTitle());
  topic.setMessage(requestDto.getMessage());
  topic.setAuthor(requestDto.getAuthor());
  topic.setCourse(requestDto.getCourse());

  // Asignar valores automáticos para `status` y `creationDate`
  topic.setStatus("ACTIVO"); 
  topic.setCreationDate(LocalDateTime.now()); 

  // Guardar el tópico en la base de datos
  topic = topicRepository.save(topic);

  // Mapear a TopicResponseDto
  TopicResponseDto responseDto = new TopicResponseDto();
  responseDto.setId(topic.getId());
  responseDto.setTitle(topic.getTitle());
  responseDto.setMessage(topic.getMessage());
  responseDto.setAuthor(topic.getAuthor());
  responseDto.setCourse(topic.getCourse());
  responseDto.setStatus(topic.getStatus());
  responseDto.setCreationDate(topic.getCreationDate()); 


  return new ApiResponse<>(true, 201, "Tópico creado exitosamente.", List.of(responseDto));
}


  public ApiResponse<TopicResponseDto> getAllTopics(String course, Integer year, Pageable pageable) {
    Page<Topic> topics;

    // Si no se proporciona filtro, obtenemos todos los tópicos
    if (course == null && year == null) {
      topics = topicRepository.findAll(pageable);
    } else if (course != null && year == null) {
      topics = topicRepository.findByCourse(course, pageable);
    } else if (course == null && year != null) {
      topics = topicRepository.findByYear(year, pageable);
    } else {
      topics = topicRepository.findByCourseAndYear(course, year, pageable);
    }

    // Convertir entidades a DTO
    List<TopicResponseDto> response = topics.getContent().stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());

    return new ApiResponse<TopicResponseDto>(true, 200, "Tópicos obtenidos exitosamente", response);
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
    return dto;
  }

}
