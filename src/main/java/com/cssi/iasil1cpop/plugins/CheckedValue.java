package com.cssi.iasil1cpop.plugins;

import com.cssi.iasil1cpop.plugins.errors.ErrorReport;

public class CheckedValue<Input> {
	private Input input;
	private ErrorReport report;
	public Input getValue() {
		return input;
	}
	public void setInput(Input input) {
		this.input = input;
	}
	public ErrorReport getReport() {
		return report;
	}
	public void setReport(ErrorReport report) {
		this.report = report;
	}
	public CheckedValue(Input input, ErrorReport report) {
		super();
		this.input = input;
		this.report = report;
	}
	
	
	

}
