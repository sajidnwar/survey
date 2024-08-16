package com.project.survey.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.survey.modal.Question;
import com.project.survey.modal.Survey;
import com.project.survey.service.SurveyService;

@RestController
@RequestMapping("surveys")
public class SurveyController {
	
	private SurveyService surveyService;
	
	public SurveyController(SurveyService surveyService) {
		this.surveyService = surveyService;
	}

	@GetMapping
	public List<Survey> retrieveAllSurveys(){
		return surveyService.retrieveAllSurveys();
	}
	
	@GetMapping("/{surveyId}")
	public Survey retrieveSurveyById(@PathVariable String surveyId){
		Survey survey = surveyService.retrieveSurveyById(surveyId);
		
		if(survey==null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		return survey;
	}
	@GetMapping("/{surveyId}/questions")
	public List<Question> retrieveAllSurveyQuestions(@PathVariable String surveyId){
		List<Question> questions = surveyService.retrieveAllSurveyQuestions(surveyId);
		
		if(questions==null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		return questions;
	}
	
	@GetMapping("/{surveyId}/questions/{questionId}")
	public Question retrieveSpecificSurveyQuestion(@PathVariable String surveyId,
			@PathVariable String questionId){
		Question question = surveyService.retrieveSpecificSurveyQuestion
										(surveyId, questionId);
		
		if(question==null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		return question;
	}

	@PostMapping("/{surveyId}/questions")
	public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId,
			@RequestBody Question question){
		
		String questionId = surveyService.addNewSurveyQuestion(surveyId, question);
		// /surveys/{surveyId}/questions/{questionId}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{questionId}").buildAndExpand(questionId).toUri();
		return ResponseEntity.created(location ).build();
		
	}

	@DeleteMapping("/{surveyId}/questions/{questionId}")
	public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyId,
			@PathVariable String questionId){
		surveyService.deleteSurveyQuestion(surveyId, questionId);
		return ResponseEntity.noContent().build();
	}
	@PutMapping("/{surveyId}/questions/{questionId}")
	public ResponseEntity<Object> updateSurveyQuestion(@PathVariable String surveyId,
			@PathVariable String questionId,
			@RequestBody Question question){
		
		surveyService.updateSurveyQuestion(surveyId, questionId, question);
		
		return ResponseEntity.noContent().build();
	}
	@PatchMapping("/{surveyId}/questions/{questionId}")
	public ResponseEntity<Object> updatePartialSurveyQuestion(@PathVariable String surveyId,
			@PathVariable String questionId,
			@RequestBody Map<String, Object> question){
		
		surveyService.updatePartialSurveyQuestion(surveyId, questionId, question);
		
		return ResponseEntity.noContent().build();
	}
}
