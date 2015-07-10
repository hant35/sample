package com.panpages.bow.service.survey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.panpages.bow.configuration.ConfigConstant;
import com.panpages.bow.model.Field;
import com.panpages.bow.model.Section;
import com.panpages.bow.model.Survey;
import com.panpages.bow.model.utils.SurveyUtils;
import com.panpages.bow.service.utils.StringUtils;

@Service("surveyCalculate")
public class SurveyCalculateImpl implements SurveyCalculate {
	
	private static final Logger logger = Logger.getLogger(SurveyCalculateImpl.class);

	@Override
	public void calculateFields(Survey survey, SurveyService surveySvc, ApplicationContext ctx) {
		if (survey == null || survey.getId() == -1) {
			return;
		}
		
		// Add fields from file
		Field uploadedFileNameField = SurveyUtils.findFieldWithFieldTemplateSlugName(SurveyFieldName.UPLOADED_FILE_NAME.getName(), survey);
		if (uploadedFileNameField != null) {
			String fileName = uploadedFileNameField.getValue();

			if (fileName != null && !fileName.trim().equals("")) {
				String uploadPath = ctx.getEnvironment().getRequiredProperty(
						ConfigConstant.UPLOAD_FOLDER_PATH.getName());
				String filePath = String.format("%1$s%2$s%3$s", uploadPath, File.separator, fileName);
				BufferedReader reader = null;

				try {
					reader = new BufferedReader(new FileReader(filePath));
					String line = null;
					int count = 0;
					while ((line = reader.readLine()) != null) {

						if (count++ == 0) {
							continue;
						}

						// Add new field
						surveySvc.addField(SurveyFieldName.FIELD_PROPOSED_KEYWORD.getName(), line, survey);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getStackTrace());
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		// Refresh survey
		survey = surveySvc.findSurveyWithId(survey.getId());
		
		///////////////////// Count indexes /////////////////////
		// Average of bid (proposed-keyword[2])
		float averageCostPerClick = -1;
		
		// Total estimated impression
		float totalEstImpression = -1;
		
		Section proposedKeywordSection = SurveyUtils.findSectionWithSectionTemplateSlugName(SurveyFieldName.SECTION_KEYWORD_PLAN.getName(), survey.getSections());
		List<Field> keywordFields = SurveyUtils.findFieldsWithFieldTemplateSlugName(SurveyFieldName.FIELD_PROPOSED_KEYWORD.getName(), proposedKeywordSection.getFields());
		
		if (keywordFields == null || keywordFields.size() == 0) {
			return;
		}
		
		Float totalAverageSearch = (float) 0;
		Float totalAverageCost = (float) 0;
		for (Field keyword:keywordFields) {
			try {
				String[] valueItems = keyword.getValue().split(",");
				
				String estImpression = StringUtils.nullValue(valueItems[1], null);
				String costPerClick = StringUtils.nullValue(valueItems.length >= 3? valueItems[2] : "0", null);
				if (estImpression == null) {
					return;
				}
			
				Float estImpressionFloat = Float.parseFloat(estImpression.trim());
				Float costPerClickFloat = Float.parseFloat(costPerClick.trim());
				
				totalAverageSearch += estImpressionFloat;
				totalAverageCost += costPerClickFloat;
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		totalEstImpression = totalAverageSearch;
		averageCostPerClick = (float)Math.round((totalAverageCost == 0? 0 : totalAverageCost/keywordFields.size())*100)/100;
		
		// Campaign click through rate or 2% 
		float assumedCTR = (float) 0.02;
		Field CTRField = SurveyUtils.findFieldWithFieldTemplateSlugName(SurveyFieldName.FIELD_CAMPAIGN_CLICK_THROUGH_RATE.getName(), survey);
		if (CTRField != null) {
			try {
				Float floatValue = Float.parseFloat(CTRField.getValue());
				assumedCTR = (float)floatValue/100;
			} catch (NumberFormatException e) {
				e.printStackTrace();
				logger.error(e.getStackTrace());
				return;
			}
		}
		
		// Estimated click on CTR
		float estClickOnCTR = -1;
		estClickOnCTR = (float)Math.round(totalEstImpression * assumedCTR * 100)/100;
		
		// Estimated monthly investment
		float estMonthlyInvestment = -1;
		estMonthlyInvestment = (float)Math.round(estClickOnCTR * averageCostPerClick * 100)/100;
		
		///////////////////// Save indexes ///////////////////////
		if (estMonthlyInvestment != -1) {
			surveySvc.addField(SurveyFieldName.FIELD_EST_MONTHLY_INVESTMENT.getName(), String.valueOf(estMonthlyInvestment), survey);
		}
		
		if (totalEstImpression != -1) {
			surveySvc.addField(SurveyFieldName.FIELD_TOTAL_EST_IMPRESSIONS.getName(), String.valueOf((int)totalEstImpression), survey);
		}
		
		if (estClickOnCTR != -1) {
			surveySvc.addField(SurveyFieldName.FIELD_EST_CLICKS_ON_CTR.getName(), String.valueOf(estClickOnCTR), survey);
		}
	}
	
	@SuppressWarnings("resource")
	public String calculateField(String fieldName, File fieldsFile, Map<String, Object> params) {
		if (fieldName != null && fieldName.trim().equalsIgnoreCase(SurveyFieldName.FIELD_TOTAL_CAMPAIGN_BUDGET.getName())) {
			float averageCostPerClick = -1;
			Float totalAverageCost = (float) 0;
			Float totalCampaignBudget = (float) -1;
			int count = 0;
			
			try {
				BufferedReader reader = new BufferedReader(new FileReader(fieldsFile));
				String line = null;
				while ((line = reader.readLine()) != null) {
					if (count++ == 0) {
						continue;
					}
					
					String[] valueItems = line.split(",");
					try {
						String estImpression = StringUtils.nullValue(valueItems[1], null);
						String costPerClick = StringUtils.nullValue(valueItems.length >= 3? valueItems[2] : "0", null);
						if (estImpression == null) {
							return null;
						}
					
						Float costPerClickFloat = Float.parseFloat(costPerClick.trim());
						totalAverageCost += costPerClickFloat;
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getStackTrace());
						return null;
					}
					
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				logger.error(e.getStackTrace());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getStackTrace());
			} 
			
			averageCostPerClick = (float)Math.round((totalAverageCost == 0? 0 : totalAverageCost/(count - 1))*100)/100;
			
			Float campaignPeriod = (float) -1;
			
			try {
				campaignPeriod = Float.parseFloat(params.get(SurveyFieldName.STEP_5_H.getName()).toString());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getStackTrace());
			}
			
			if (campaignPeriod == -1) {
				return null;
			}
			
			Float estMonthlyBudget = (float) -1;
			Float monthlyTraffic = (float) -1;
			
			Float traffic = (float) -1;
			Float leads = (float) -1;
			Float newAdvertisers = (float) -1;
			Float leadConversionRate = (float) 0.2;
			Float revenueIncreased = (float) -1;
			Float avgRevenuePerCust = (float) -1;
			
			Float enquiryRate = (float) -1;
			
			try {
				enquiryRate = Float.parseFloat(params.get(SurveyFieldName.STEP_5_D.getName()).toString());
				revenueIncreased = Float.parseFloat(params.get(SurveyFieldName.STEP_5_A.getName()).toString());
				avgRevenuePerCust = Float.parseFloat(params.get(SurveyFieldName.STEP_5_B.getName()).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (enquiryRate == -1) {
				return null;
			}
			
			newAdvertisers = revenueIncreased/avgRevenuePerCust;
			leads = (newAdvertisers/enquiryRate)*100;
			traffic = leads/leadConversionRate;
			monthlyTraffic = (float) Math.round(traffic/campaignPeriod);
			
			estMonthlyBudget = monthlyTraffic * averageCostPerClick;
			totalCampaignBudget = estMonthlyBudget * campaignPeriod;
			
			return String.valueOf(totalCampaignBudget);
		}
		
		return null;
	}
	
	public Map<String, String> calculateFields(String fieldName, File fieldsFile, Map<String, Object> params) {
		if (fieldName != null && fieldName.trim().equalsIgnoreCase(SurveyFieldName.FIELD_TOTAL_CAMPAIGN_BUDGET.getName())) {
			float averageCostPerClick = -1;
			Float totalAverageCost = (float) 0;
			Float totalCampaignBudget = (float) -1;
			Float totalAverageSearch = (float) 0;
			float totalEstImpression = -1;
			int count = 0;
			
			try {
				@SuppressWarnings("resource")
				BufferedReader reader = new BufferedReader(new FileReader(fieldsFile));
				String line = null;
				while ((line = reader.readLine()) != null) {
					if (count++ == 0) {
						continue;
					}
					
					String[] valueItems = line.split(",");
					try {
						String estImpression = StringUtils.nullValue(valueItems[1], null);
						String costPerClick = StringUtils.nullValue(valueItems.length >= 3? valueItems[2] : "0", null);
						if (estImpression == null) {
							return null;
						}
						
						Float estImpressionFloat = Float.parseFloat(estImpression.trim());
						Float costPerClickFloat = Float.parseFloat(costPerClick.trim());
						totalAverageCost += costPerClickFloat;
						totalAverageSearch += estImpressionFloat;
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getStackTrace());
						return null;
					}
					
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				logger.error(e.getStackTrace());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getStackTrace());
			} 
			
			averageCostPerClick = (float)Math.round((totalAverageCost == 0? 0 : totalAverageCost/(count - 1))*100)/100;
			
			Float campaignPeriod = (float) -1;
			
			totalEstImpression = totalAverageSearch;
			averageCostPerClick = (float)Math.round((totalAverageCost == 0? 0 : totalAverageCost/(count - 1))*100)/100;
			
			// Estimated click on CTR
			float estClickOnCTR = -1;
			float assumedCTR = (float) 0.02;
			try {
				assumedCTR = (float)Float.parseFloat(params.get(SurveyFieldName.FIELD_CAMPAIGN_CLICK_THROUGH_RATE.getName()).toString())/100;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getStackTrace());
			}
			
			estClickOnCTR = (float)Math.round(totalEstImpression * assumedCTR * 100)/100;
			
			// Estimated monthly investment
			float estMonthlyInvestment = -1;
			estMonthlyInvestment = (float)Math.round(estClickOnCTR * averageCostPerClick * 100)/100;
			
			try {
				campaignPeriod = Float.parseFloat(params.get(SurveyFieldName.STEP_5_H.getName()).toString());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getStackTrace());
			}
			
			if (campaignPeriod == -1) {
				return null;
			}
			
			Float totalGoogleBudget = estMonthlyInvestment * campaignPeriod;
			
			Float estMonthlyBudget = (float) -1;
			Float monthlyTraffic = (float) -1;
			
			Float traffic = (float) -1;
			Float leads = (float) -1;
			Float newAdvertisers = (float) -1;
			Float leadConversionRate = (float) 0.2;
			Float revenueIncreased = (float) -1;
			Float avgRevenuePerCust = (float) -1;
			
			Float enquiryRate = (float) -1;
			
			try {
				enquiryRate = Float.parseFloat(params.get(SurveyFieldName.STEP_5_D.getName()).toString());
				revenueIncreased = Float.parseFloat(params.get(SurveyFieldName.STEP_5_A.getName()).toString());
				avgRevenuePerCust = Float.parseFloat(params.get(SurveyFieldName.STEP_5_B.getName()).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (enquiryRate == -1) {
				return null;
			}
			
			newAdvertisers = revenueIncreased/avgRevenuePerCust;
			leads = (newAdvertisers/enquiryRate)*100;
			traffic = leads/leadConversionRate;
			monthlyTraffic = (float) Math.round(traffic/campaignPeriod);
			
			estMonthlyBudget = monthlyTraffic * averageCostPerClick;
			totalCampaignBudget = estMonthlyBudget * campaignPeriod;
			
			Map<String,String> result = new HashMap<String, String>();
			result.put(fieldName, String.valueOf(totalCampaignBudget));
			result.put(SurveyFieldName.FIELD_TOTAL_GOOGLE_BUDGET.getName(), String.valueOf(totalGoogleBudget));
			
			return result;
		}
		
		return null;
	}
	
}
