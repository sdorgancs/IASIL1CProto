/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssi.iasil1cpop.plugins.errors;

import com.cssi.pdap.dpi.plugins.DataFlowProgessEvent;
import com.cssi.pdap.dpi.plugins.DataProgressStatus;
import com.cssi.pdap.dpi.plugins.IDataFlowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author sdorgan
 */
public class ErrorReport {
	List<Error> errors = new ArrayList<>();

	public void addError(Error e) {
		errors.add(e);
	}

	public List<Error> errors() {
		return errors;
	}
	/**
	 * Computes the aggregated severity
	 * @return the aggregated severity
	 */
	public ErrorSeverity severity() {
		boolean warning = false;
		boolean error = false;
		boolean critical = false;

		for (Error e : errors) {
			if (e.criticity == ErrorSeverity.CRITICAL) {
				critical = true;
				break;
			}
			if (e.criticity == ErrorSeverity.ERROR) {
				error = true;
			} else if (e.criticity == ErrorSeverity.WARNING) {
				warning = true;
			}
		}

		if (critical)
			return ErrorSeverity.CRITICAL;
		if (error)
			return ErrorSeverity.ERROR;
		if (warning)
			return ErrorSeverity.WARNING;
		return ErrorSeverity.NO_ERROR;
	}
	
	public ErrorReport duplicate(){
		ErrorReport er = new ErrorReport();
		Collections.copy(this.errors, er.errors);
		return er;
	}

	public IDataFlowEvent createEvent(Object obj) {
		return new DataFlowProgessEvent(obj.getClass().getName(), "", DataProgressStatus.NONE, 0);
	}
}
