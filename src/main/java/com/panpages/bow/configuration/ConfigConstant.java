package com.panpages.bow.configuration;

public enum ConfigConstant {
	
	JDBC_DRIVER_CLASS_NAME("jdbc.driverClassName"),
	JDBC_URL("jdbc.url"),
	JDBC_USERNAME("jdbc.username"),
	JDBC_PASSWORD("jdbc.password"),
	HIBERNATE_DIALECT("hibernate.dialect"),
	HIBERNATE_SHOW_SQL("hibernate.show_sql"),
	HIBERNATE_FORMAT_SQL("hibernate.format_sql"),
	PREFIX_TEMPLATE_NAME("prefix_template_name"),
	TEMPLATE_PATH("template_path"),
	TEMPLATE_FOLDER_NAME_PREFIX("template_folder_name_prefix"),
	REPORT_OUTPUT_PREFIX_NAME("report_output_prefix_name"),
	REPORT_TEMPLATE_PATH("report_template_path"),
	REPORT_OUTPUT_PATH("report_output_path"),
	EXCEL_REPORT_MAILTO("excel_report_mailto"),
	EXCEL_OUTPUT_PATH("excel_output_path"),
	EXCEL_REPORT_KEY("excel_report_key"),
	REPORT_OUTPUT_BASE_URL("report_output_base_url"),
	MAIL_TEMPLATE_PATH("mail_template_path"), 
	MAIL_HOST("mail_host"), 
	MAIL_PORT("mail_port"),  
	MAIL_USERNAME("mail_username"), 
	MAIL_PASSWORD("mail_password"), 
	UPLOAD_FOLDER_PATH("upload_folder_path"),
	NOTIFICATION_MAIL_SUBJECT("notification_mail_subject"), 
	NOTIFICATION_MAIL_TEMPLATE_NAME("notification_mail_template_name"), 
	SURVEY_PDF_URL("survey-pdf-url"), 
	BASE_URL("base_url"), 
	CONSULTANT_EMAIL("consultant-email-address"),
	ADVERTISER_COMPANY_NAME("advertiser-company-name"), 
	SURVEY_FULFILLED_DATE("survey-fulfilled-date"), 
	CONSULTANT_NAME("consultant-name"), 
	TIME_ZONE("time_zone");
	
	private String name;

	ConfigConstant(String value) {
		this.name = value;
	}

	public String getName() {
		return name;
	}
	
}
