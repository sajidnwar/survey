package com.project.survey.modal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

	private String id;
	private String description;
	private List<String> options;
	private String correctAnswer;
}
