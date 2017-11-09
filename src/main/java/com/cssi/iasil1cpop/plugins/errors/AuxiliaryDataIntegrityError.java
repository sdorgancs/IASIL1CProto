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
public class AuxiliaryDataIntegrityError extends AuxiliaryDataMissingError {
	String fieldIdentifier;

	public AuxiliaryDataIntegrityError(ErrorSeverity criticity, String description, String dataIdentifier,
			String fieldIdentifier) {
		super(criticity, description, dataIdentifier);
		this.fieldIdentifier = fieldIdentifier;
	}

}
