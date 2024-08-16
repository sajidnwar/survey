package com.project.survey.modal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Survey {

	private String id;
	private String title;
	private String description;
	private List<Question> questions;
}
