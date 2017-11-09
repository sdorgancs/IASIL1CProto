/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssi.iasil1cpop.plugins.errors;

/**
 *
 * @author sdorgan
 */
public class DataIntegrityError extends DataMissingError {
	String fieldIndentifier;

	public DataIntegrityError(ErrorSeverity criticity, String description, String productIdentifier,
			String fieldIdentifier) {
		super(criticity, description, productIdentifier);
		this.fieldIndentifier = fieldIdentifier;
	}

}
