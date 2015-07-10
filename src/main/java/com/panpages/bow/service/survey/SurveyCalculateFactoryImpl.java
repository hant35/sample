package com.panpages.bow.service.survey;

import org.springframework.stereotype.Service;

@Service("surveyCalculateFactory")
public class SurveyCalculateFactoryImpl implements SurveyCalculateFactory {

	@Override
	public SurveyCalculate createSurveyCalculator(int surveyId) {
		
		return new SurveyCalculateImpl();
	}

}
