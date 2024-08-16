package com.project.survey.serviceImpl;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.project.survey.modal.Question;
import com.project.survey.modal.Survey;
import com.project.survey.service.SurveyService;

@Service
public class SurveyServiceImpl implements SurveyService{

	
	private static final List<Survey> surveys = new ArrayList<>();

	static {

		var question1 = new Question("Question1", "Most Popular Cloud Platform Today",
				List.of("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		var question2 = new Question("Question2", "Fastest Growing Cloud Platform",
				List.of("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
		var question3 = new Question("Question3", "Most Popular DevOps Tool",
				List.of("Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

		List<Question> questions = new ArrayList<>(List.of(question1, question2, question3));

		var survey = new Survey("Survey1", "My Favorite Survey", "Description of the Survey", questions);

		surveys.add(survey);

	}

	@Override
	public List<Survey> retrieveAllSurveys() {
		return surveys;
	}

	@Override
	public Survey retrieveSurveyById(String surveyId) {
		Optional<Survey> survey = surveys.stream().filter(s -> s.getId().equalsIgnoreCase(surveyId)).findFirst();
		
		return survey.orElse(null);
	}
	
	public List<Question> retrieveAllSurveyQuestions(String surveyId) {
		Survey survey = retrieveSurveyById(surveyId);

		if (survey == null)
			return null;

		return survey.getQuestions();
	}

	public Question retrieveSpecificSurveyQuestion(String surveyId, String questionId) {

		List<Question> surveyQuestions = retrieveAllSurveyQuestions(surveyId);

		if (surveyQuestions == null)
			return null;

		Optional<Question> optionalQuestion = surveyQuestions.stream()
				.filter(q -> q.getId().equalsIgnoreCase(questionId)).findFirst();

        return optionalQuestion.orElse(null);

    }

	public String addNewSurveyQuestion(String surveyId, Question question) {
		List<Question> questions = retrieveAllSurveyQuestions(surveyId);
		question.setId(generateRandomId());
		questions.add(question);
		return question.getId();
	}

	private String generateRandomId() {
		SecureRandom secureRandom = new SecureRandom();
		String randomId = new BigInteger(32, secureRandom).toString();
		return randomId;
	}

	public String deleteSurveyQuestion(String surveyId, String questionId) {

		List<Question> surveyQuestions = retrieveAllSurveyQuestions(surveyId);

		if (surveyQuestions == null)
			return null;
		
		boolean removed = surveyQuestions.removeIf(q -> q.getId().equalsIgnoreCase(questionId));
		
		if(!removed) return null;

		return questionId;
	}
	
	public void updateSurveyQuestion(String surveyId, String questionId, Question question) {
		question.setId(questionId);
		List<Question> questions = retrieveAllSurveyQuestions(surveyId);
		questions.removeIf(q -> q.getId().equalsIgnoreCase(questionId));
		questions.add(question);
	}

	@Override
	public void updatePartialSurveyQuestion(String surveyId, String questionId, Map<String, Object> question) {
		List<Question> questions = retrieveAllSurveyQuestions(surveyId);
		
		if (questions == null)
			return;

		Optional<Question> optionalQuestion = questions.stream()
				.filter(q -> q.getId().equalsIgnoreCase(questionId)).findFirst();
		if(optionalQuestion.isEmpty()) {
			return;
		}
		Question question2 = optionalQuestion.get();
		question.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Question.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, question2, value);
            }
        });
		
	}
	
}
