package com.project.survey.service;

import java.util.List;
import java.util.Map;

import com.project.survey.modal.Question;
import com.project.survey.modal.Survey;

public interface SurveyService {
	
	public List<Survey> retrieveAllSurveys();
	
	public Survey retrieveSurveyById(String surveyId);
	
	public List<Question> retrieveAllSurveyQuestions(String surveyId);
	
	public Question retrieveSpecificSurveyQuestion(String surveyId, String questionId);
	
	public String addNewSurveyQuestion(String surveyId, Question question);
	
	public String deleteSurveyQuestion(String surveyId, String questionId);
	
	public void updateSurveyQuestion(String surveyId, String questionId, Question question);
	
	public void updatePartialSurveyQuestion(String surveyId, String questionId, Map<String, Object> question);

}
