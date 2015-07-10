package com.panpages.bow.service.report;

public enum ReportType {
	
	PDF("pdf"),
	HTML("html"),
	DOC("doc"),
	CSV("csv");
	
	private String name;
	
	ReportType(String value) {
		this.name = value;
	}

	public String getName() {
		return name;
	}
}
