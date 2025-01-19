package com.alura.forohub.forohub.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alura.forohub.forohub.dto.ApiResponse;
import com.alura.forohub.forohub.dto.TopicRequestDto;
import com.alura.forohub.forohub.dto.TopicResponseDto;
import com.alura.forohub.forohub.service.TopicService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController {
  private final TopicService topicService;

  @PostMapping
  public ResponseEntity<ApiResponse<TopicResponseDto>> createTopic(@Valid @RequestBody TopicRequestDto requestDto) {
    ApiResponse<TopicResponseDto> response = topicService.createTopic(requestDto);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @GetMapping
  public ApiResponse<TopicResponseDto> getAllTopics(
      @RequestParam(required = false) String course,
      @RequestParam(required = false) Integer year,
      @PageableDefault(sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable) {

    return topicService.getAllTopics(course, year, pageable);
  }

  @GetMapping("/{id}")
  public ApiResponse<TopicResponseDto> getTopicDetails(@PathVariable Long id) {
    return topicService.getTopicDetails(id);
  }

  @PutMapping("/{id}")
  public ApiResponse<TopicResponseDto> updateTopic(@PathVariable Long id,
      @RequestBody @Valid TopicRequestDto requestDto) {
    return topicService.updateTopic(id, requestDto);

  }

}